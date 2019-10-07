/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.customer.controller;

import com.itgarden.website.exam.model.Answer;
import com.itgarden.website.exam.model.Exam;
import com.itgarden.website.exam.model.Mark;
import com.itgarden.website.exam.model.Question;
import com.itgarden.website.exam.model.Test;
import com.itgarden.website.exam.ripository.AnswerRepository;
import com.itgarden.website.exam.ripository.ExamRepository;
import com.itgarden.website.exam.ripository.MarkRepository;
import com.itgarden.website.exam.ripository.QuestionRepository;
import com.itgarden.website.exam.ripository.TestRepository;
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

@RequestMapping("/student-mark")
@PreAuthorize("hasAuthority('student-mark')")
public class MarkStudentController {

    @Autowired
    ExamRepository examRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    TestRepository testRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    MarkRepository markRepository;

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("attribute", "value");
        return "view.name";
    }

    @RequestMapping("/save/{tid}")
    public String save(Model model, @PathVariable Long tid, Mark mark, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        Long answerid = Long.valueOf(mark.getValue());

        Answer answer = answerRepository.findByIdAndQuestion(answerid, mark.getQuestion());

        // not select answer
        if (answer != null) {

            mark.setAnswerTitle(answer.getTitle());

            mark.setValue(answer.getValue());

            markRepository.save(mark);

            Test testinfo = testRepository.getOne(tid);
            //  Question Number
            int total_test_answer = testinfo.getMark().size();

            int total_question = testinfo.getExam().getQuestion().size();

            // Question end or null
            if (total_test_answer == total_question) {

                redirectAttributes.addFlashAttribute("message", "Successfully Completed Your Test.");

                return "redirect:/student-test/testdashboard/{tid}";

            } else {
                redirectAttributes.addFlashAttribute("message", "Successfully saved.");

                return "redirect:/student-test/openquestion/{tid}";
            }

        } else {
            redirectAttributes.addFlashAttribute("message", "Please select answer.");
            return "redirect:/student-test/openquestion/{tid}";
        }

    }
}
