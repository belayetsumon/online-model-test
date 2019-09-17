/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.vendor.controller;

import com.itgarden.website.exam.model.Exam;
import com.itgarden.website.exam.model.Question;
import com.itgarden.website.exam.ripository.AnswerRepository;
import com.itgarden.website.exam.ripository.ExamRepository;
import com.itgarden.website.exam.ripository.QuestionRepository;
import com.itgarden.website.model.enumvalue.Status;
import com.itgarden.website.module.user.services.LoggedUserService;
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
@RequestMapping("/instructor-question")
public class QuestionInstructorController {

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
        return "catalog/question/index";
    }

    @RequestMapping("/create/{examid}")
    public String create(Model model, @PathVariable Long examid, Question question) {
        Exam exam = new Exam();

        exam.setId(examid);
        question.setExam(exam);

        int questionNo = questionRepository.findByExamOrderByIdDesc(exam).size() + 1;

        question.setQuestionno(questionNo);

        model.addAttribute("statuslist", Status.values());
        model.addAttribute("productsubcategorylist", productsubcategoryRepository.findByStatus(Status.Active));

//        Users userss = new Users();
//        userss.setId(loggedUserService.activeUserid());
//        exam.setUserId(userss);
        return "instructor/question/add";

    }

    @RequestMapping("/save/{examid}")
    public String create(Model model, @PathVariable Long examid, @Valid Question question, BindingResult bindingResult, RedirectAttributes redirectAttributes,
            @RequestParam("pic") MultipartFile pic
    ) {

        if (bindingResult.hasErrors()) {

            Exam exam = new Exam();

            exam.setId(examid);
            question.setExam(exam);

            int questionNo = questionRepository.findByExamOrderByIdDesc(exam).size() + 1;

            question.setQuestionno(questionNo);

            model.addAttribute("statuslist", Status.values());

            return "instructor/question/add";
        }

        if (!pic.isEmpty()) {
            try {
                byte[] bytes = pic.getBytes();

                // Creating the directory to store file
                File dir = new File(properties.getRootPath());
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                long datenow = System.currentTimeMillis();
                String filename = datenow + "_" + pic.getOriginalFilename();
                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + filename);

//                BufferedOutputStream stream = new BufferedOutputStream(
//                        new FileOutputStream(serverFile));
//                stream.write(bytes);
//                stream.close();
                BufferedImage originalImage;

                originalImage = ImageIO.read(pic.getInputStream());

                Thumbnails.of(originalImage).forceSize(800, 600).toFile(serverFile);

                model.addAttribute("message", "You successfully uploaded");

                question.setImageName(filename);

                questionRepository.save(question);

                redirectAttributes.addFlashAttribute("message", "Successfully saved.");
                question.getExam();
                return "redirect:/instructor-exam/question-by-exam/{examid}";

            } catch (Exception e) {

                Exam exam = new Exam();

                exam.setId(examid);
                question.setExam(exam);

                int questionNo = questionRepository.findByExamOrderByIdDesc(exam).size() + 1;

                question.setQuestionno(questionNo);

                model.addAttribute("statuslist", Status.values());

                redirectAttributes.addFlashAttribute("message", pic.getOriginalFilename() + " => " + e.getMessage());
                return "redirect:/instructor-exam/question-by-exam/{examid}";
            }
        } else if (pic.isEmpty() && question.getId() != null) {

            Question questions = questionRepository.getOne(question.getId());

            question.setImageName(questions.getImageName());

            questionRepository.save(question);

            redirectAttributes.addFlashAttribute("message", "Successfully saved.");

            return "redirect:/instructor-exam/question-by-exam/{examid}";

        } else {
            questionRepository.save(question);
            redirectAttributes.addFlashAttribute("message", "Successfully saved .Image is empty.");
            return "redirect:/instructor-exam/question-by-exam/{examid}";
        }

    }

    @RequestMapping("/details/{id}")
    public String details(Model model, @PathVariable Long id, Question question) {

        model.addAttribute("question_details", questionRepository.getOne(id));
        return "instructor/question/question_details";
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, Question question) {
        model.addAttribute("question", questionRepository.getOne(id));
        model.addAttribute("statuslist", Status.values());
        return "instructor/question/add";
    }

    @RequestMapping("/delete/{id}")

    public String delete(Model model, @PathVariable Long id, Question question, RedirectAttributes redirectAttributes) {

        question = questionRepository.getOne(id);

        File file = new File(properties.getRootPath() + File.separator + question.getImageName());
        redirectAttributes.addAttribute("examid", question.getExam().getId());

        questionRepository.deleteById(id);

        file.delete();

        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");

        return "redirect:/instructor-exam/question-by-exam/{examid}";
    }

    @RequestMapping("/answer-by-question/{qid}")
    public String answer_by_question(Model model, @PathVariable Long qid, Question question) {
        question.setId(qid);
        model.addAttribute("answerlist", answerRepository.findByQuestion(question));

        model.addAttribute("qinfo", questionRepository.getOne(qid));

        return "instructor/question/answer-by-question";
    }

}
