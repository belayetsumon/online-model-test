/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.customer.controller;

import com.itgarden.website.exam.model.Exam;
import com.itgarden.website.exam.ripository.ExamRepository;
import com.itgarden.website.module.user.model.Users;
import com.itgarden.website.module.user.services.LoggedUserService;
import com.itgarden.website.order.model.SalesOrder;
import com.itgarden.website.order.model.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.itgarden.website.order.repository.SalesOrderRepository;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 *
 * @author User
 */
@Controller
@RequestMapping("/customer-order")
@PreAuthorize("hasAuthority('customer-order')")
public class OrderCustomerController {

    @Autowired
    LoggedUserService loggedUserService;

    @Autowired
    SalesOrderRepository salesOrderRepository;

    @Autowired
    ExamRepository examRepository;

    @RequestMapping(value = {"", "/", "/index"})
    public String save(Model model, SalesOrder order, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        Users userId = new Users();

        userId.setId(loggedUserService.activeUserid());

        model.addAttribute("orderlist", salesOrderRepository.findByCustomer(userId));

        return "student/order/index";
    }

    @RequestMapping(value = {"/details/{oid}"})
    public String details(Model model, @PathVariable Long oid, SalesOrder salesOrder) {

        model.addAttribute("orderdetails", salesOrderRepository.getOne(oid));

        return "student/order/order_details";
    }

}
