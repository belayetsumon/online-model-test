/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.vendor.controller;

import com.itgarden.website.module.user.model.Users;
import com.itgarden.website.module.user.services.LoggedUserService;
import com.itgarden.website.vendor.model.Vendorprofile;
import com.itgarden.website.vendor.repository.VendorprofileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author User
 */
@Controller
@RequestMapping("/vendorprofile")
public class VendorProfileController {

    @Autowired
    LoggedUserService loggedUserService;

    @Autowired
    VendorprofileRepository vendorprofileRepository;

    @RequestMapping(value = {"", "/", "/index"})
    public String index(Model model) {

        Users users = new Users();
        users.setId(loggedUserService.activeUserid());

        model.addAttribute("vendorprofile", vendorprofileRepository.findByUserId(users));

        return "vendor/profile/index";
    }

    @RequestMapping(value = {"create"})
    public String create(Model model, Vendorprofile vendorprofile) {

        Users users = new Users();
        users.setId(loggedUserService.activeUserid());
        vendorprofile.setUserId(users);

       // model.addAttribute("vendorprofile", vendorprofileRepository.findByUserId(users));
        return "vendor/profile/profile_add";
    }
    
    
    


}
