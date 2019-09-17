/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.controller;

import com.itgarden.website.model.Jobcategory;
import com.itgarden.website.model.Productcategory;
import com.itgarden.website.model.enumvalue.Status;
import com.itgarden.website.ripository.JobcategoryRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author User
 */
@Controller
@RequestMapping("/jobcategory")
public class JobcategoryController {

    @Autowired
    JobcategoryRepository jobcategoryRepository;

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("jobcategorylist", jobcategoryRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        return "career/jobcategory/index";
    }

    @RequestMapping("/create")
    public String create(Model model, Jobcategory jobcategory) {
        model.addAttribute("statuslist", Status.values());
        return "career/jobcategory/add";
    }

    @RequestMapping("/save")
    public String create(Model model, @Valid Jobcategory jobcategory, BindingResult bindingResult, RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            return "career/jobcategory/add";
        }
        jobcategoryRepository.save(jobcategory);
        redirectAttributes.addFlashAttribute("message", "Successfully saved.");
        return "redirect:/jobcategory/index";
    }

    @RequestMapping("/details/{id}")
    public String create(Model model, @PathVariable Long id, Jobcategory jobcategory) {
        model.addAttribute("jobcategory_details", jobcategoryRepository.getOne(id));
        return "career/jobcategory/jobcategory_details";

    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, Jobcategory jobcategory) {
        model.addAttribute("jobcategory", jobcategoryRepository.getOne(id));
        model.addAttribute("statuslist", Status.values());
        return "career/jobcategory/add";
    }

    @RequestMapping("/delete/{id}")
   public String delete(Model model, @PathVariable Long id, Jobcategory jobcategory, RedirectAttributes redirectAttributes) {
        jobcategoryRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");
        return "redirect:/jobcategory/index";
    }

}
