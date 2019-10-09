/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.adminvendor.controller;

import com.itgarden.website.exam.ripository.ExamRepository;
import com.itgarden.website.module.user.model.Role;
import com.itgarden.website.module.user.model.Users;
import com.itgarden.website.module.user.ripository.RoleRepository;
import com.itgarden.website.module.user.ripository.UsersRepository;
import com.itgarden.website.order.model.OrderStatus;
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
@RequestMapping("/admin-vendor")
//@PreAuthorize("hasAuthority('admin-vendor')")
public class AdminVendorController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ExamRepository examRepository;

    @RequestMapping(value = {"", "/", "/index"})
    public String customerlist(Model model) {

        Role instructor = roleRepository.findBySlug("instructor");
        model.addAttribute("instructorlist", usersRepository.findByRole(instructor));
        return "admin/vendor/index";
    }

    @RequestMapping("/exam-by-instructor/{iid}")
    public String exam_by_instructor(Model model, @PathVariable Long iid) {

        Users userId = new Users();
        userId.setId(iid);
        model.addAttribute("examlist", examRepository.findByUserIdOrderByIdDesc(userId));

        return "admin/vendor/exam-by-instructor";
    }

}
