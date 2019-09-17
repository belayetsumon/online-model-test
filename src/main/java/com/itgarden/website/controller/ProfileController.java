/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.controller;

import com.itgarden.website.model.Gender;
import com.itgarden.website.model.Profile;
import com.itgarden.website.module.user.model.Status;
import com.itgarden.website.module.user.model.Users;
import com.itgarden.website.module.user.ripository.UsersRepository;
import com.itgarden.website.module.user.services.LoggedUserService;
import com.itgarden.website.ripository.BloodGroupRepository;
import com.itgarden.website.ripository.ProfileRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author User
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    BloodGroupRepository bloodGroupRepository;

    @Autowired
    LoggedUserService loggedUserService;
    
     @Autowired
    UsersRepository usersRepository;
     

    @RequestMapping(value = {"","/", "/index"})
    public String index(Model model, Profile profile) {

        Users userss = new Users();
        profile.setUserId(userss);

        model.addAttribute("userinfo", usersRepository.findByIdAndStatus(loggedUserService.activeUserid(), Status.Active));
        
        model.addAttribute("genderlist", Gender.values());
        return "frontview/profile";
    }

    @RequestMapping("/create")
    public String add(Model model, Profile profile) {

        Users userss = new Users();
        userss.setId(loggedUserService.activeUserid());
        profile.setUserId(userss);

        model.addAttribute("bloodgrouplist", bloodGroupRepository.findAll());
        model.addAttribute("genderlist", Gender.values());
        return "frontview/profile_add";
    }

    @RequestMapping("/save")
    //@Transactional
    public String save(Model model, @Valid Profile profile, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {

            Users userss = new Users();
            userss.setId(loggedUserService.activeUserid());
            profile.setUserId(userss);

            model.addAttribute("bloodgrouplist", bloodGroupRepository.findAll());
            model.addAttribute("genderlist", Gender.values());
             return "frontview/profile_add";
        }

        profile.setCreatedBy(loggedUserService.activeUserName());
        profile.setModifiedBy(loggedUserService.activeUserName());

        profileRepository.save(profile);
        redirectAttributes.addFlashAttribute("message", "Successfully saved.");
        return "redirect:/profile/index";

    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, Profile profile) {

        model.addAttribute("profile", profileRepository.getOne(id));

        model.addAttribute("bloodgrouplist", bloodGroupRepository.findAll());

        model.addAttribute("genderlist", Gender.values());

        return "frontview/profile_add";
    }

    @RequestMapping("/delete/{id}")

    public String delete(Model model, @PathVariable Long id, Profile profile, RedirectAttributes redirectAttributes) {

        profileRepository.deleteById(id);

        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");

        return "redirect:/profile/index";
    }

}
