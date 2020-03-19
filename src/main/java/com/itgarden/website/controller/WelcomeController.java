/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.controller;

import com.itgarden.website.exam.ripository.ExamRepository;
import com.itgarden.website.exam.ripository.QuestionRepository;
import com.itgarden.website.model.Contact;
import com.itgarden.website.model.Productcategory;
import com.itgarden.website.module.user.model.Role;
import com.itgarden.website.module.user.model.Status;
import com.itgarden.website.module.user.ripository.RoleRepository;
import com.itgarden.website.module.user.ripository.UsersRepository;
import com.itgarden.website.ripository.BatchRepository;
import com.itgarden.website.ripository.GalleryRepository;
import com.itgarden.website.ripository.ImageGalleryRepository;
import com.itgarden.website.ripository.OurclientsRepository;
import com.itgarden.website.ripository.ProductcategoryRepository;
import com.itgarden.website.ripository.ProductsubcategoryRepository;
import com.itgarden.website.ripository.ProfileRepository;
import com.itgarden.website.ripository.TestimonialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author User
 */
@Controller
public class WelcomeController {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    BatchRepository batchRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    GalleryRepository galleryRepository;

    @Autowired
    ImageGalleryRepository imageGalleryRepository;

    @Autowired
    ProductcategoryRepository productcategoryRepository;

    @Autowired
    ProductsubcategoryRepository productsubcategoryRepository;

    @Autowired
    OurclientsRepository ourclientsRepository;

    @Autowired
    TestimonialRepository testimonialRepository;

    @Autowired
    ExamRepository examRepository;

    @Autowired
    QuestionRepository questionRepository;

    @RequestMapping(value = {"", "/", "/index"})

    public String index(Model model, Contact contact) {
        model.addAttribute("contact", contact);
        Pageable pageable = new PageRequest(0, 15);
        model.addAttribute("latestuserlist", usersRepository.findByStatusAndProfileImageNotNullOrderByIdDesc(Status.Active, pageable));

        model.addAttribute("productcategorylist", productcategoryRepository.findByStatus(com.itgarden.website.model.enumvalue.Status.Active));

        model.addAttribute("clientslist", ourclientsRepository.findByStatusOrderByIdDesc(com.itgarden.website.model.enumvalue.Status.Active));

        Pageable testimonial_pageable = new PageRequest(0, 2);
        model.addAttribute("testimoniallist", testimonialRepository.findByStatusOrderByIdDesc(com.itgarden.website.model.enumvalue.Status.Active, testimonial_pageable));

        Role customer = roleRepository.findBySlug("customer");

        model.addAttribute("totalStudent", usersRepository.findByRole(customer).size() + 5380);

        Role instructor = roleRepository.findBySlug("instructor");

        model.addAttribute("totalInstructor", usersRepository.findByRole(instructor).size() + 273);

        model.addAttribute("totalExam", examRepository.count());

        model.addAttribute("totalQuestion", questionRepository.count());

        // public University 
        Productcategory publicuniversityadmission = productcategoryRepository.findBySlug("public-university-admission-test");

        model.addAttribute("publicuniversityadmission", productsubcategoryRepository.findByProductcategory(publicuniversityadmission));

        // jobs
        Productcategory job = productcategoryRepository.findBySlug("job");

        model.addAttribute("joblist", productsubcategoryRepository.findByProductcategory(job));

        // science-technology-university-admission-test
        Productcategory science_technology = productcategoryRepository.findBySlug("science-technology-university-admission-test");

        model.addAttribute("science_technology", productsubcategoryRepository.findByProductcategory(science_technology));

       // public-engineering-universities-admission
                
        Productcategory engineering_universities = productcategoryRepository.findBySlug("public-engineering-universities-admission");

        model.addAttribute("engineering_universities", productsubcategoryRepository.findByProductcategory(engineering_universities));

        Pageable latestExam = new PageRequest(0, 12, Direction.ASC, "id");

        // Latest Exam
        model.addAttribute("examlist", examRepository.findByStatusOrderByIdDesc(latestExam, com.itgarden.website.model.enumvalue.Status.Active));
        
        // Laatest sale
        //model.addAttribute("latestsale", examRepository.findByStatusOrderByIdDesc(latestExam, com.itgarden.website.model.enumvalue.Status.Active));

        return "welcome/welcome";

    }

}
