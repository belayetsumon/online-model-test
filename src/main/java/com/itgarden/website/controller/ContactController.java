/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.controller;

import com.itgarden.website.model.Contact;
import com.itgarden.website.model.enumvalue.Status;
import com.itgarden.website.ripository.ContactRepository;
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
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    ContactRepository contactRepository;

    @RequestMapping(value = {"","/", "/index"})
    public String index(Model model) {
        model.addAttribute("contactlist", contactRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        return "contact/index";
    }

    @RequestMapping("/create")
    public String create(Model model, Contact contact) {
        model.addAttribute("statuslist", Status.values());
        return "contact/add";
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, Contact contact) {

        model.addAttribute("contact", contactRepository.getOne(id));
        model.addAttribute("statuslist", Status.values());
        return "contact/add";
    }

    @RequestMapping("/save")
    public String create(Model model, @Valid Contact contact, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("statuslist", Status.values());
            return "contact/add";
        }

        contactRepository.save(contact);
        return "redirect:/contact/index";
    }

    @RequestMapping("/details/{id}")
    public String create(Model model, @PathVariable Long id, Contact contact) {

        model.addAttribute("contact_details", contactRepository.getOne(id));

        return "contact/contact_details";

    }

    @RequestMapping("/delete/{id}")

    public String delete(Model model, @PathVariable Long id, Contact contact, RedirectAttributes redirectAttributes) {

        contactRepository.deleteById(id);

        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");

        return "redirect:/contact/index";
    }

}
