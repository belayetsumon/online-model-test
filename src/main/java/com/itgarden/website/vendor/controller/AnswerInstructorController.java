/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.vendor.controller;

import com.itgarden.website.exam.model.Answer;
import com.itgarden.website.exam.model.Question;
import com.itgarden.website.exam.ripository.AnswerRepository;
import com.itgarden.website.exam.ripository.ExamRepository;
import com.itgarden.website.exam.ripository.QuestionRepository;
import com.itgarden.website.module.user.services.LoggedUserService;
import com.itgarden.website.ripository.ProductcategoryRepository;
import com.itgarden.website.ripository.ProductsubcategoryRepository;
import com.itgarden.website.services.StorageProperties;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/instructor-answer")
@PreAuthorize("hasAuthority('instructor-answer')")
public class AnswerInstructorController {
    
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
    AnswerRepository answerRepository;
    
    @RequestMapping(value = {"", "/", "/index"})
    public String index(Model model) {
        model.addAttribute("examlist", examRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        return "instructor/answer/index";
    }
    
    @RequestMapping("/create/{qid}")
    public String create(Model model, @PathVariable Long qid, Answer answer) {
        Question question = new Question();
        
        question.setId(qid);
        answer.setQuestion(question);
        
        int ansNo = answerRepository.findByQuestion(question).size() + 1;
        
        answer.setAnswerno(ansNo);
        
        Question questionno = questionRepository.getOne(qid);
        
        answer.setQuestionno(questionno.getQuestionno());
        
        return "instructor/answer/add";
    }
    
    @RequestMapping("/save/{qid}")
    public String create(Model model, @Valid Answer answer, BindingResult bindingResult, RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            
            return "instructor/answer/add";
        }
        answerRepository.save(answer);
        redirectAttributes.addFlashAttribute("message", "Successfully saved.");
        
        return "redirect:/instructor-question/answer-by-question/{qid}";
    }
    
    @RequestMapping("/details/{id}")
    public String details(Model model, @PathVariable Long id, Answer answer) {
        model.addAttribute("answer_details", answerRepository.getOne(id));
        return "instructor/answer/answer_details";
    }
    
    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, Answer answer) {
        model.addAttribute("answer", answerRepository.getOne(id));
        return "instructor/answer/add";
    }
    
    @RequestMapping("/delete/{id}")
    
    public String delete(Model model, @PathVariable Long id, Answer answer, RedirectAttributes redirectAttributes) {
        
        answer = answerRepository.getOne(id);
        
        redirectAttributes.addAttribute("qid", answer.getQuestion().getId());
        
        answerRepository.deleteById(id);
        
        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");
        
        return "redirect:/instructor-question/answer-by-question/{qid}";
    }
    
}
