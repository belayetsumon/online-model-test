/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.controller;

import com.itgarden.website.model.Subscriber;
import com.itgarden.website.model.enumvalue.Status;
import com.itgarden.website.ripository.SubscriberRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/subscriber")
public class SubscriberController {

    @Autowired
    SubscriberRepository subscriberRepository;

    @RequestMapping(value = {"", "/", "/index"})
    public String index(Model model) {
        model.addAttribute("subscriberlist", subscriberRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        return "subscriber/index";
    }

    @RequestMapping("/create")
    public String create(Model model, Subscriber subscriber) {
        model.addAttribute("statuslist", Status.values());
        return "subscriber/add";
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, Subscriber subscriber) {
        model.addAttribute("subscriber", subscriberRepository.getOne(id));
        model.addAttribute("statuslist", Status.values());
        return "subscriber/add";
    }

    @RequestMapping("/save")
    public String create(Model model, @Valid Subscriber subscriber, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("statuslist", Status.values());
            return "subscriber/add";
        }
        subscriberRepository.save(subscriber);
        return "redirect:/subscriber/index";
    }

    @RequestMapping("/details/{id}")
    public String create(Model model, @PathVariable Long id, Subscriber subscriber) {
        model.addAttribute("subscriber_details", subscriberRepository.getOne(id));
        return "subscriber/subscriberdetails";
    }

    @RequestMapping("/delete/{id}")

    public String delete(Model model, @PathVariable Long id, Subscriber subscriber, RedirectAttributes redirectAttributes) {

        subscriberRepository.deleteById(id);

        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");

        return "redirect:/subscriber/index";
    }

}
