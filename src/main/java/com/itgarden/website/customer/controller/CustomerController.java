/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.customer.controller;

import com.itgarden.website.model.Profile;
import com.itgarden.website.module.user.model.Users;
import com.itgarden.website.module.user.ripository.UsersRepository;
import com.itgarden.website.module.user.services.LoggedUserService;
import com.itgarden.website.order.model.OrderStatus;
import com.itgarden.website.order.repository.SalesOrderRepository;
import com.itgarden.website.ripository.BloodGroupRepository;
import com.itgarden.website.ripository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author User
 */
@Controller
@RequestMapping("/customer")
@PreAuthorize("hasAuthority('customer')")
public class CustomerController {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    BloodGroupRepository bloodGroupRepository;

    @Autowired
    LoggedUserService loggedUserService;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    SalesOrderRepository salesOrderRepository;

    @RequestMapping(value = {"", "/", "/index", "dashboards"})
    public String index(Model model, Profile profile) {

        model.addAttribute("username", loggedUserService.activeUserName());
        Users userId = new Users();
        userId.setId(loggedUserService.activeUserid());
        model.addAttribute("orderlist", salesOrderRepository.findByCustomer(userId));

        model.addAttribute("orderlist_panding", salesOrderRepository.findByCustomerAndStatusOrderByIdDesc(userId, OrderStatus.Pending));

        model.addAttribute("examlist", salesOrderRepository.findByCustomerAndStatusOrderByIdDesc(userId, OrderStatus.Complete));

        return "customer/index";
    }

}
