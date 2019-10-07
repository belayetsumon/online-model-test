/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.module.user.controller;

import com.itgarden.website.module.user.model.Module;
import com.itgarden.website.module.user.ripository.ModuleRepository;
import com.itgarden.website.module.user.ripository.PrivilegeRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author User
 */
@Controller
@RequestMapping("/module")
//@PreAuthorize("hasAuthority('module')")
public class ModuleController {

    @Autowired
    PrivilegeRepository privilegeRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @RequestMapping(value = {"", "/", "/index"})

    public String index(Model model, Module module) {

        model.addAttribute("list", moduleRepository.findAll());

        return "/user/module";

    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, Module module) {

        model.addAttribute("module", moduleRepository.findById(id));

        model.addAttribute("list", moduleRepository.findAll());

        return "/user/module";
    }

    @RequestMapping("/save")
    public String save(Model model, @Valid Module module, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("list", moduleRepository.findAll());
            return "/user/module";
        }

        moduleRepository.save(module);

        return "redirect:/module/index";
    }

    @RequestMapping("/delete/{id}")

    public String delete(Model model, @PathVariable Long id, Module module) {

        moduleRepository.deleteById(id);

        return "redirect:/module/index";
    }

}
