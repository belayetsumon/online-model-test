/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author User
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @RequestMapping(value = {"", "/", "/index"})

    public String index(Model model) {

        model.addAttribute("orderlist", "order");

        return "order/order/index";
    }

    
    
    @RequestMapping(value = {"create"})

    public String create(Model model) {

        model.addAttribute("orderlist", "order");

        return "order/order/create";
    }
}
