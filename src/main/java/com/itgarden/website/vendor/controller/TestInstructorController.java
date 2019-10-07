/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.vendor.controller;

import com.itgarden.website.exam.model.Exam;
import com.itgarden.website.exam.model.Mark;
import com.itgarden.website.exam.model.Question;
import com.itgarden.website.exam.model.Test;
import com.itgarden.website.exam.ripository.ExamRepository;
import com.itgarden.website.exam.ripository.QuestionRepository;
import com.itgarden.website.exam.ripository.TestRepository;
import com.itgarden.website.model.enumvalue.Status;
import com.itgarden.website.module.user.model.Users;
import com.itgarden.website.module.user.services.LoggedUserService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/instructor-test")
@PreAuthorize("hasAuthority('instructor-test')")
public class TestInstructorController {

    @Autowired
    LoggedUserService loggedUserService;

    @Autowired
    ExamRepository examRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    TestRepository testRepository;

    @RequestMapping("/url")

    public String index(Model model) {

        model.addAttribute("attribute", "value");

        return "view.name";
    }

    @RequestMapping("/save/{eid}")
    public String create(Model model, @PathVariable Long eid, Test test, BindingResult bindingResult, RedirectAttributes redirectAttributes
    ) {

        Exam exam = examRepository.getOne(eid);
        test.setExam(exam);

        Users userss = new Users();
        userss.setId(loggedUserService.activeUserid());
        test.setUserId(userss);

        test.setStatus(Status.Active);

        testRepository.save(test);

        redirectAttributes.addFlashAttribute("message", "Your Test Have Created. Lets start.");

        return "redirect:/instructor-exam/details/{eid}";
    }

    @RequestMapping("/testdashboard/{tid}")
    public String testview(Model model, @PathVariable Long tid, Test test) {

        Test testinfo = testRepository.getOne(tid);

        model.addAttribute("testinfo", testinfo);

        Exam examinfo = examRepository.getOne(testinfo.getExam().getId());

        model.addAttribute("examinfo", examinfo);

        return "instructor/test/testdashboard";
    }

    @RequestMapping("/openquestion/{tid}")

    public String openquestion(Model model, @PathVariable Long tid, Test test, Mark mark, RedirectAttributes redirectAttributes) {
        Test testinfo = testRepository.getOne(tid);

        int total_test_answer = testinfo.getMark().size();

        int total_question = testinfo.getExam().getQuestion().size();

        if (total_test_answer == total_question) {

            redirectAttributes.addFlashAttribute("message", "Successfully Completed Your Test.");

            return "redirect:/instructor-test/testdashboard/{tid}";

        } else {

            model.addAttribute("testinfo", testinfo);

            //  Question Number
            int question_number = testinfo.getMark().size() + 1;

            //  Exam number
            Exam exam = testinfo.getExam();

            // Question 
            Question question = questionRepository.findByExamAndQuestionno(exam, question_number);

            model.addAttribute("qinfo", question);

            mark.setTest(testinfo);

            mark.setQuestion(question);

            mark.setQuestionno(question.getQuestionno());

            mark.setQuestionTitle(question.getTitle());

            return "instructor/test/openquestion";
        }

    }

}
