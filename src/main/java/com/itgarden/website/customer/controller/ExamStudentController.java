/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.customer.controller;

import com.itgarden.website.exam.model.Exam;
import com.itgarden.website.exam.model.Lavelstatus;
import com.itgarden.website.model.enumvalue.Status;
import com.itgarden.website.module.user.model.Users;
import com.itgarden.website.module.user.services.LoggedUserService;
import com.itgarden.website.exam.ripository.ExamRepository;
import com.itgarden.website.exam.ripository.QuestionRepository;
import com.itgarden.website.exam.ripository.TestRepository;
import com.itgarden.website.ripository.ProductcategoryRepository;
import com.itgarden.website.ripository.ProductsubcategoryRepository;
import com.itgarden.website.services.StorageProperties;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.validation.Valid;
import net.coobird.thumbnailator.Thumbnails;
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
@RequestMapping("/student-exam")
public class ExamStudentController {

    @Autowired
    StorageProperties properties;

    @Autowired
    LoggedUserService loggedUserService;

    @Autowired
    ProductcategoryRepository productcategoryRepository;

    @Autowired
    ProductsubcategoryRepository productsubcategoryRepository;

    @Autowired
    ExamRepository examRepository;

    @Autowired
    QuestionRepository questionRepository;
    
    @Autowired
    TestRepository testRepository;

    @RequestMapping(value = {"", "/", "/index"})
    public String index(Model model) {
        model.addAttribute("examlist", examRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        return "student/exam/index";
    }

    @RequestMapping("/details/{id}")
    public String create(Model model, @PathVariable Long id, Exam exam) {

        model.addAttribute("exam_details", examRepository.getOne(id));
        
        Exam examid = examRepository.getOne(id);
        
        model.addAttribute("testlist", testRepository.findByExamOrderByIdDesc(examid));

        return "student/exam/exam_details";

    }
    
 
    @RequestMapping("/question-by-exam/{examid}")
    public String question_by_exam(Model model, @PathVariable Long examid, Exam exam) {

        exam.setId(examid);
        model.addAttribute("questionlist", questionRepository.findByExamOrderByIdDesc(exam));

        model.addAttribute("examinfo", examRepository.getOne(examid));

        return "student/exam/question-by-exam";
    }

}
