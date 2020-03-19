/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.globalcontroller;

import com.itgarden.website.model.cart.CartItem;
import com.itgarden.website.module.user.ripository.UsersRepository;
import java.util.List;
import javax.servlet.http.HttpSession;
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

    @ModelAttribute
    public void shopingcart(Model model, HttpSession session) {

        if (session.getAttribute("sessioncart") != null) {

            List<CartItem> cartitem = (List<CartItem>) session.getAttribute("sessioncart");

            model.addAttribute("totaltest", cartitem.size());

        } else {

            model.addAttribute("totaltest", "0");
        }

    }

}
