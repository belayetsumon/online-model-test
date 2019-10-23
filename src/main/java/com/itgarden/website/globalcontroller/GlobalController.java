/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.globalcontroller;

import com.itgarden.website.module.user.ripository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 *
 * @author User
 */
@ControllerAdvice
public class GlobalController {

    @Autowired
    UsersRepository usersRepository;

    @ModelAttribute
    public void addAttributes(Model model) {

//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if(!auth==null){
//        Users users = usersRepository.findByEmail(auth.getName());
//        
//        model.addAttribute("username", users.getName());
    }

}
