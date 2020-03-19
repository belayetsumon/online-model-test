/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.exam.controller;

import com.itgarden.website.exam.model.Exam;
import com.itgarden.website.exam.model.Lavelstatus;
import com.itgarden.website.model.enumvalue.Status;
import com.itgarden.website.module.user.model.Users;
import com.itgarden.website.module.user.services.LoggedUserService;
import com.itgarden.website.exam.ripository.ExamRepository;
import com.itgarden.website.exam.ripository.QuestionRepository;
import com.itgarden.website.exam.ripository.TestRepository;
import com.itgarden.website.model.Productcategory;
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
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/exam")
//@PreAuthorize("hasAuthority('exam')")
public class ExamController {

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
        return "catalog/exam/index";
    }

    @RequestMapping("/categorylist")
    public String categorylist(Model model, Productcategory productcategory) {

        model.addAttribute("productcategorylist", productcategoryRepository.findByStatus(Status.Active));

        return "catalog/exam/categorylist";
    }

    @RequestMapping("/create/{cid}")
    public String create(Model model, @PathVariable Long cid, Exam exam) {
        model.addAttribute("statuslist", Status.values());

        Productcategory productcategory = new Productcategory();
        productcategory.setId(cid);
        model.addAttribute("productsubcategorylist", productsubcategoryRepository.findByProductcategory(productcategory));


        Users userss = new Users();
        userss.setId(loggedUserService.activeUserid());
        exam.setUserId(userss);

        model.addAttribute("lavel", Lavelstatus.values());
        return "catalog/exam/add";
    }

    @RequestMapping("/save")
    public String create(Model model, @Valid Exam exam, BindingResult bindingResult, RedirectAttributes redirectAttributes,
            @RequestParam("pic") MultipartFile pic
    ) {

        if (bindingResult.hasErrors()) {

            Users userss = new Users();
            userss.setId(loggedUserService.activeUserid());
            exam.setUserId(userss);
            model.addAttribute("statuslist", Status.values());
            model.addAttribute("productsubcategorylist", productsubcategoryRepository.findByStatus(Status.Active));
            return "catalog/exam/add";
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

                exam.setImageName(filename);

                examRepository.save(exam);

                redirectAttributes.addFlashAttribute("message", "Successfully saved.");
                return "redirect:/exam/index";
            } catch (Exception e) {

                model.addAttribute("statuslist", Status.values());
                model.addAttribute("lavel", Lavelstatus.values());
                model.addAttribute("productsubcategorylist", productsubcategoryRepository.findByStatus(Status.Active));

                redirectAttributes.addFlashAttribute("message", pic.getOriginalFilename() + " => " + e.getMessage());
                return "redirect:/exam/index";
            }
        } else if (pic.isEmpty() && exam.getId() != null) {

            Exam exams = examRepository.getOne(exam.getId());

            exam.setImageName(exams.getImageName());

            examRepository.save(exam);

            redirectAttributes.addFlashAttribute("message", "Successfully saved.");

            return "redirect:/exam/index";

        } else {
            examRepository.save(exam);
            redirectAttributes.addFlashAttribute("message", "File empty");
            return "redirect:/exam/index";
        }
//        newsRepository.save(news);
//        return "redirect:/news/index";
    }

    @RequestMapping("/details/{id}")
    public String details(Model model, @PathVariable Long id, Exam exam) {

        model.addAttribute("exam_details", examRepository.getOne(id));

        Exam examid = examRepository.getOne(id);

        model.addAttribute("testlist", testRepository.findByExamOrderByIdDesc(examid));

        return "catalog/exam/exam_details";

    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, Exam exam) {
        model.addAttribute("exam", examRepository.getOne(id));
        model.addAttribute("lavel", Lavelstatus.values());
        model.addAttribute("statuslist", Status.values());
        model.addAttribute("productsubcategorylist", productsubcategoryRepository.findByStatus(Status.Active));
        return "catalog/exam/add";
    }

    @RequestMapping("/delete/{id}")

    public String delete(Model model, @PathVariable Long id, Exam exam, RedirectAttributes redirectAttributes) {

        exam = examRepository.getOne(id);
        File file = new File(properties.getRootPath() + File.separator + exam.getImageName());

        examRepository.deleteById(id);

        file.delete();

        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");

        return "redirect:/exam/index";
    }

    @RequestMapping("/question-by-exam/{examid}")
    public String question_by_exam(Model model, @PathVariable Long examid, Exam exam) {

        exam.setId(examid);
        model.addAttribute("questionlist", questionRepository.findByExamOrderByIdDesc(exam));

        model.addAttribute("examinfo", examRepository.getOne(examid));

        return "catalog/exam/question-by-exam";
    }

}
