/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.vendor.controller;

import com.itgarden.website.exam.model.Exam;
import com.itgarden.website.exam.ripository.ExamRepository;
import com.itgarden.website.module.user.model.Users;
import com.itgarden.website.module.user.services.LoggedUserService;
import com.itgarden.website.order.model.OrderStatus;
import com.itgarden.website.order.repository.SalesOrderRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author User
 */
@Controller
@RequestMapping("/instructor")
@PreAuthorize("hasAuthority('instructor')")
public class InstructorController {

    @Autowired
    LoggedUserService loggedUserService;

    @Autowired
    SalesOrderRepository salesOrderRepository;

    @Autowired
    ExamRepository examRepository;

    @RequestMapping(value = {"", "/", "/index", "dashboards"})
    public String index(Model model) {
        model.addAttribute("attribute", "value");

        Users userId = new Users();
        userId.setId(loggedUserService.activeUserid());
        // model.addAttribute("orderlist", salesOrderRepository.findByCustomer(userId));

        Pageable pageable = new PageRequest(0, 20, Sort.Direction.DESC, "id");

        List<Exam> totalexamsales = examRepository.findByUserIdAndOrderItemSalesOrderStatus(pageable, userId, OrderStatus.Complete);

        double totalincome = 0.00;

        if (!totalexamsales.isEmpty()) {

            for (int i = 0; i < totalexamsales.size(); i++) {

                totalincome += totalexamsales.get(i).getPrice();
            }
        } else {

            totalincome = 0.00;
        }

        int income = (int) (totalincome * 30 / 100);

        
        model.addAttribute("totalincome", income);
        
        return "instructor/dashboards";
    }

}
