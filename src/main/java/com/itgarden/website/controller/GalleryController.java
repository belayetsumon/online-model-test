/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.controller;

import com.itgarden.website.model.Gallery;
import com.itgarden.website.ripository.GalleryRepository;
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
@RequestMapping("/gallery")
public class GalleryController {

    @Autowired
    GalleryRepository galleryRepository;

    @Autowired
    StorageProperties properties;

    @RequestMapping(value = {"","/", "/index"})
    public String index(Model model) {

        model.addAttribute("gallerylist", galleryRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));

        return "gallery/index";
    }

    @RequestMapping("/create")
    public String create(Model model, Gallery gallery) {

        model.addAttribute("attribute", "value");

        return "gallery/add";
    }

    @RequestMapping("/save")
    public String create(Model model, @Valid Gallery gallery, BindingResult bindingResult, RedirectAttributes redirectAttributes,
            @RequestParam("pic") MultipartFile pic) {

        if (bindingResult.hasErrors()) {
            return "gallery/add";
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
                        .size(300, 225)
                        .toFile(serverFile);

                model.addAttribute("message", "You successfully uploaded");

                gallery.setImageName(filename);

                galleryRepository.save(gallery);

                return "redirect:/gallery/index";

            } catch (Exception e) {

                redirectAttributes.addFlashAttribute("message", pic.getOriginalFilename() + " => " + e.getMessage());

                return "redirect:/gallery/index";
            }
        } else if (pic.isEmpty() && gallery.getId() != null) {

            gallery = galleryRepository.getOne(gallery.getId());

            gallery.setImageName(gallery.getImageName());

            galleryRepository.save(gallery);

            redirectAttributes.addFlashAttribute("message", "Successfully saved.");

            return "redirect:/gallery/index";

        } else {
            galleryRepository.save(gallery);

            redirectAttributes.addFlashAttribute("message", "File empty");

            return "redirect:/gallery/index";
        }

    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, Gallery gallery) {

        model.addAttribute("gallery", galleryRepository.getOne(id));

        return "gallery/add";
    }

    @RequestMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id, Gallery gallery, RedirectAttributes redirectAttributes) {

        File file = new File(properties.getRootPath() + File.separator + gallery.getImageName());
        file.delete();

        galleryRepository.deleteById(id);

        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");

        return "redirect:/gallery/index";
    }

}
