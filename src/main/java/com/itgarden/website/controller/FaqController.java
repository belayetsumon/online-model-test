/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.controller;
import com.itgarden.website.model.Faq;
import com.itgarden.website.model.enumvalue.Status;
import com.itgarden.website.ripository.FaqRepository;
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
@RequestMapping("/faq")
public class FaqController {

    @Autowired
    FaqRepository faqRepository;

    @RequestMapping(value = {"","/", "/index"})
    public String index(Model model) {
        model.addAttribute("faqlist", faqRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        return "faq/index";
    }

    @RequestMapping("/create")
    public String create(Model model, Faq faq) {
        model.addAttribute("statuslist", Status.values());
        return "faq/add";
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, Faq faq) {
        model.addAttribute("faq", faqRepository.getOne(id));
        model.addAttribute("statuslist", Status.values());
        return "faq/add";
    }

    @RequestMapping("/save")
    public String create(Model model, @Valid Faq faq, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("statuslist", Status.values());
            return "faq/add";
        }
        faqRepository.save(faq);
        return "redirect:/faq/index";
    }

    @RequestMapping("/details/{id}")
    public String create(Model model, @PathVariable Long id, Faq faq) {
        model.addAttribute("faq_details", faqRepository.getOne(id));
        return "faq/faq_details";
    }

    @RequestMapping("/delete/{id}")

    public String delete(Model model, @PathVariable Long id, Faq faq, RedirectAttributes redirectAttributes) {

        faqRepository.deleteById(id);

        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");

        return "redirect:/faq/index";
    }

}
