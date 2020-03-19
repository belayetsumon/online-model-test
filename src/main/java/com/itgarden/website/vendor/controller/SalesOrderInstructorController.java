/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.vendor.controller;

import com.itgarden.website.exam.ripository.ExamRepository;

import com.itgarden.website.module.user.model.Users;
import com.itgarden.website.module.user.services.LoggedUserService;
import com.itgarden.website.order.model.OrderStatus;
import com.itgarden.website.order.model.SalesOrder;
import com.itgarden.website.order.repository.SalesOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author User
 */
@Controller
@RequestMapping("/vendor-order")
@PreAuthorize("hasAuthority('vendor-order')")
public class SalesOrderInstructorController {

    @Autowired
    LoggedUserService loggedUserService;

    @Autowired
    SalesOrderRepository salesOrderRepository;

    @Autowired
    ExamRepository examRepository;

    @RequestMapping(value = {"", "/", "/index"})

    public String index(Model model) {

        Users userId = new Users();
        userId.setId(loggedUserService.activeUserid());
        // model.addAttribute("orderlist", salesOrderRepository.findByCustomer(userId));

        Pageable pageable = new PageRequest(0, 20, Sort.Direction.DESC, "id");

        model.addAttribute("orderlist", examRepository.findByUserIdAndOrderItemSalesOrderStatus(pageable, userId,OrderStatus.Complete ));

        return "instructor/sales/index";
    }

    @RequestMapping(value = {"/details/{oid}"})
    public String details(Model model, @PathVariable Long oid, SalesOrder salesOrder) {

        model.addAttribute("orderdetails", salesOrderRepository.getOne(oid));

        return "instructor/sales/order_details";
    }

}
