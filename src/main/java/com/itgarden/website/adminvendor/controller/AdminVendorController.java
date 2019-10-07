/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.adminvendor.controller;

import com.itgarden.website.module.user.model.Role;
import com.itgarden.website.module.user.ripository.RoleRepository;
import com.itgarden.website.module.user.ripository.UsersRepository;
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
@RequestMapping("/admin-vendor")
@PreAuthorize("hasAuthority('admin-vendor')")
public class AdminVendorController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    RoleRepository roleRepository;

    @RequestMapping(value = {"", "/", "/index"})
    public String customerlist(Model model) {

        Role instructor = roleRepository.findBySlug("instructor");
        model.addAttribute("instructorlist", usersRepository.findByRole(instructor));
        return "admin/vendor/index";
    }

}