/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.module.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Md Belayet Hossin
 */
@Controller
@RequestMapping("userdetails")
public class UserDetailsController {

    @RequestMapping("/index")
    public String page(Model model) {
        model.addAttribute("attribute", "value");
        return "pims/userdetails/index";
    }

}
