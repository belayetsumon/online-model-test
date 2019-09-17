/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.controller;

import com.itgarden.website.model.Job;
import com.itgarden.website.model.enumvalue.Status;
import com.itgarden.website.ripository.JobRepository;
import com.itgarden.website.ripository.JobcategoryRepository;
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

@RequestMapping("/jobs")
public class JobController {

    @Autowired
    JobcategoryRepository jobcategoryRepository;

    @Autowired
    JobRepository jobRepository;

    @RequestMapping(value = {"","/", "/index"})
    public String index(Model model) {
        model.addAttribute("joblist", jobRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        return "career/job/index";
    }

    @RequestMapping("/create")
    public String create(Model model, Job job) {
        model.addAttribute("jobcategorylist", jobcategoryRepository.findAll());
        model.addAttribute("statuslist", Status.values());
        return "career/job/add";
    }

    @RequestMapping("/save")
    public String create(Model model, @Valid Job job, BindingResult bindingResult, RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("jobcategorylist", jobcategoryRepository.findAll());
            model.addAttribute("statuslist", Status.values());
            return "career/job/add";
        }
        jobRepository.save(job);
        redirectAttributes.addFlashAttribute("message", "Successfully saved.");
        return "redirect:/jobs/index";
    }

    @RequestMapping("/details/{id}")
    public String create(Model model, @PathVariable Long id, Job job) {
        model.addAttribute("jobcategorylist", jobcategoryRepository.findAll());
        model.addAttribute("job_details", jobRepository.getOne(id));
        return "career/job/jobs_details";

    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, Job job) {
        model.addAttribute("job", jobRepository.getOne(id));
        model.addAttribute("jobcategorylist", jobcategoryRepository.findAll());
        model.addAttribute("statuslist", Status.values());
        return "career/job/add";
    }

    @RequestMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id, Job job, RedirectAttributes redirectAttributes) {
        jobRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");
        return "redirect:/jobs/index";
    }

}
