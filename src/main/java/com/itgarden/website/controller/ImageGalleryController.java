/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.controller;

import com.itgarden.website.model.ImageGallery;
import com.itgarden.website.ripository.GalleryRepository;
import com.itgarden.website.ripository.ImageGalleryRepository;
import com.itgarden.website.services.StorageProperties;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.validation.Valid;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.catalina.connector.InputBuffer;
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
@RequestMapping("/images")
public class ImageGalleryController {

    @Autowired
    StorageProperties properties;

    @Autowired
    GalleryRepository galleryRepository;

    @Autowired
    ImageGalleryRepository imageGalleryRepository;

    @RequestMapping(value = {"","/", "/index"})
    public String index(Model model) {
        model.addAttribute("imageslist", imageGalleryRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));

        return "galleryimage/index";
    }

    @RequestMapping("/create")
    public String create(Model model, ImageGallery imageGallery) {

        model.addAttribute("gallerylist", galleryRepository.findAll());

        return "galleryimage/add";

    }

    @RequestMapping("/save")
    public String create(Model model, @Valid ImageGallery imageGallery, BindingResult bindingResult, RedirectAttributes redirectAttributes,
            @RequestParam("pic") MultipartFile pic
    ) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("gallerylist", galleryRepository.findAll());
            return "galleryimage/add";
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

                Thumbnails.of(originalImage)
                        .size(800, 600)
                        .toFile(serverFile);

                model.addAttribute("message", "You successfully uploaded");

                imageGallery.setImageName(filename);

                imageGalleryRepository.save(imageGallery);
                return "redirect:/images/index";
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("message", pic.getOriginalFilename() + " => " + e.getMessage());
                return "redirect:/images/index";
            }
        } else if (pic.isEmpty() && imageGallery.getId() != null) {

            imageGallery = imageGalleryRepository.getOne(imageGallery.getId());

            imageGallery.setImageName(imageGallery.getImageName());

            imageGalleryRepository.save(imageGallery);

            redirectAttributes.addFlashAttribute("message", "Successfully saved.");

            return "redirect:/images/index";

        } else {

            redirectAttributes.addFlashAttribute("message", "File empty");
            return "redirect:/images/index";
        }
//        newsRepository.save(news);
//        return "redirect:/news/index";
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, ImageGallery imageGallery) {

        model.addAttribute("imageGallery", imageGalleryRepository.getOne(id));

        model.addAttribute("gallerylist", galleryRepository.findAll());

        return "galleryimage/add";
    }

    @RequestMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id, ImageGallery imageGallery, RedirectAttributes redirectAttributes) {

        imageGallery = imageGalleryRepository.getOne(id);

        File file = new File(properties.getRootPath() + File.separator + imageGallery.getImageName());

        file.delete();

        imageGalleryRepository.deleteById(id);

        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");

        return "redirect:/images/index";
    }

}
