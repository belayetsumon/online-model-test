/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.exam.controller;

import com.itgarden.website.exam.model.CommentsStatus;
import com.itgarden.website.exam.model.Exam;
import com.itgarden.website.exam.model.ExamComment;
import com.itgarden.website.exam.model.Question;
import com.itgarden.website.exam.ripository.ExamCommentRepository;
import com.itgarden.website.model.enumvalue.Status;
import com.itgarden.website.module.user.model.Users;
import com.itgarden.website.module.user.services.LoggedUserService;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/examcomment")
public class ExamCommentController {

    @Autowired
    ExamCommentRepository examCommentRepository;

    @Autowired
    LoggedUserService loggedUserService;

    @RequestMapping("/index")
    public String index(Model model) {

        Pageable page = PageRequest.of(0, 500, Sort.Direction.DESC, "id");
        model.addAttribute("commentslist", examCommentRepository.findAll(page));
        return "catalog/comment/index";
    }

    @RequestMapping("/create/{examid}")
    public String create(Model model, @PathVariable Long examid, Exam exam, ExamComment examComment) {

        exam.setId(examid);
        examComment.setExam(exam);
        Users users = new Users();
        users.setId(loggedUserService.activeUserid());
        examComment.setUserId(users);

        model.addAttribute("commentstatus", CommentsStatus.values());

        return "catalog/comment/add";
    }

    @RequestMapping("/save/{examid}")
    public String save(Model model, @PathVariable Long examid, @Valid ExamComment examComment, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {

            Exam exam = new Exam();
            exam.setId(examid);
            examComment.setExam(exam);

            Users users = new Users();
            users.setId(loggedUserService.activeUserid());
            examComment.setUserId(users);

            model.addAttribute("commentstatus", CommentsStatus.values());

            return "catalog/comment/add";
        }

        redirectAttributes.addFlashAttribute("message", "Successfully saved.");
        examCommentRepository.save(examComment);
        return "redirect:/examcomment/index";
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, ExamComment examComment) {

        model.addAttribute("examComment", examCommentRepository.getOne(id));

        model.addAttribute("commentstatus", CommentsStatus.values());

        return "catalog/comment/add";
    }

    @RequestMapping("/delete/{id}")

    public String delete(Model model, @PathVariable Long id, ExamComment examComment, RedirectAttributes redirectAttributes) {

        examCommentRepository.deleteById(id);

        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");

        return "redirect:/examcomment/index";
    }

}
