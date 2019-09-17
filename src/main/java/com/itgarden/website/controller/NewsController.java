/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.controller;

import com.itgarden.website.model.News;
import com.itgarden.website.model.enumvalue.Status;
import com.itgarden.website.ripository.NewsRepository;
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
@RequestMapping("/news")
public class NewsController {

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    StorageProperties properties;

    @RequestMapping(value = {"", "/", "/index"})
    public String index(Model model) {
        model.addAttribute("newslist", newsRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        return "news/index";
    }

    @RequestMapping("/create")
    public String create(Model model, News news) {
        model.addAttribute("statuslist", Status.values());
        return "news/add";
    }

    @RequestMapping("/save")
    public String create(Model model, @Valid News news, BindingResult bindingResult, RedirectAttributes redirectAttributes,
            @RequestParam("pic") MultipartFile pic
    ) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("statuslist", Status.values());
            return "news/add";
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

                news.setImageName(filename);

                newsRepository.save(news);

                redirectAttributes.addFlashAttribute("message", "Successfully saved.");
                return "redirect:/news/index";
            } catch (Exception e) {

                model.addAttribute("statuslist", Status.values());

                redirectAttributes.addFlashAttribute("message", pic.getOriginalFilename() + " => " + e.getMessage());
                return "redirect:/news/index";
            }
        } else if (pic.isEmpty() && news.getId() != null) {

            News newes = newsRepository.getOne(news.getId());

            news.setImageName(newes.getImageName());

            newsRepository.save(news);

            redirectAttributes.addFlashAttribute("message", "Successfully saved.");

            return "redirect:/news/index";

        } else {
            newsRepository.save(news);
            redirectAttributes.addFlashAttribute("message", "File empty");
            return "redirect:/news/index";
        }
//        newsRepository.save(news);
//        return "redirect:/news/index";
    }

    @RequestMapping("/details/{id}")
    public String create(Model model, @PathVariable Long id, News news) {

        model.addAttribute("news_details", newsRepository.getOne(id));

        return "news/news_details";

    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, News news) {
        model.addAttribute("news", newsRepository.getOne(id));
        model.addAttribute("statuslist", Status.values());
        return "news/add";
    }

    @RequestMapping("/delete/{id}")

    public String delete(Model model, @PathVariable Long id, News news, RedirectAttributes redirectAttributes) {

        news = newsRepository.getOne(id);
        File file = new File(properties.getRootPath() + File.separator + news.getImageName());

        file.delete();

        newsRepository.deleteById(id);

        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");

        return "redirect:/news/index";
    }

}
