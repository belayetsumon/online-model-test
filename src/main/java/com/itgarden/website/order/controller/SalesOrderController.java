/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.order.controller;

import com.itgarden.website.order.model.SalesOrder;
import com.itgarden.website.order.repository.SalesOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/order")
@PreAuthorize("hasAuthority('order')")
public class SalesOrderController {

    @Autowired
    SalesOrderRepository salesOrderRepository;

    @RequestMapping(value = {"", "/", "/index"})

    public String index(Model model) {

        model.addAttribute("orderlist", salesOrderRepository.findAll());

        return "order/order/index";
    }

    @RequestMapping(value = {"/details/{oid}"})
    public String details(Model model, @PathVariable Long oid, SalesOrder salesOrder) {

        model.addAttribute("orderdetails", salesOrderRepository.getOne(oid));

        return "order/order/order_details";
    }

    @RequestMapping(value = {"create"})
    public String create(Model model) {
        model.addAttribute("orderlist", "order");
        return "order/order/create";
    }
}
