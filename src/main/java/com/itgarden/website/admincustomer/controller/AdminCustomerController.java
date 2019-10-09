/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.admincustomer.controller;

import com.itgarden.website.module.user.model.Role;
import com.itgarden.website.module.user.model.Users;
import com.itgarden.website.module.user.ripository.RoleRepository;
import com.itgarden.website.module.user.ripository.UsersRepository;
import com.itgarden.website.order.repository.SalesOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author User
 */
@Controller
@RequestMapping("/admin-customer")
//@PreAuthorize("hasAuthority('admin-customer')")
public class AdminCustomerController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    SalesOrderRepository salesOrderRepository;

    @RequestMapping(value = {"", "/", "/index"})
    public String customerlist(Model model) {

        Role customer = roleRepository.findBySlug("customer");

        model.addAttribute("customerlist", usersRepository.findByRole(customer));
        return "admin/customer/index";
    }

    @RequestMapping("/order-by-customer/{cid}")
    public String orderbycustomer(Model model, @PathVariable Long cid) {

        Users customer = new Users();

        customer.setId(cid);

        model.addAttribute("orderlist", salesOrderRepository.findByCustomer(customer));

        return "admin/customer/order-by-customer";
    }

    @RequestMapping("/order-details/{oid}")
    public String order_details(Model model, @PathVariable Long oid) {

        model.addAttribute("orderdetails", salesOrderRepository.getOne(oid));

        return "admin/customer/order_details";
    }

}
