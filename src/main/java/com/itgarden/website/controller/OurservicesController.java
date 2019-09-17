/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.controller;

import com.itgarden.website.model.Ourservices;
import com.itgarden.website.model.enumvalue.Status;
import com.itgarden.website.ripository.OurservicesRepository;
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
@RequestMapping("/services")
public class OurservicesController {

    @Autowired
    StorageProperties properties;

    @Autowired
    OurservicesRepository ourservicesRepository;

    @RequestMapping(value = {"","/", "/index"})
    public String index(Model model) {
        model.addAttribute("servicelist", ourservicesRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        return "services/index";
    }

    @RequestMapping("/create")
    public String create(Model model, Ourservices ourservices) {
        model.addAttribute("statuslist", Status.values());
        return "services/add";
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, Ourservices ourservices) {

        model.addAttribute("ourservices", ourservicesRepository.getOne(id));
        model.addAttribute("statuslist", Status.values());
        return "services/add";
    }

    @RequestMapping("/save")
    public String create(Model model, @Valid Ourservices ourservices, BindingResult bindingResult, RedirectAttributes redirectAttributes,
            @RequestParam("pic") MultipartFile pic
    ) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("statuslist", Status.values());
            return "services/add";
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

                ourservices.setImageName(filename);

                ourservicesRepository.save(ourservices);

                redirectAttributes.addFlashAttribute("message", "Successfully saved.");
                return "redirect:/services/index";
            } catch (Exception e) {

                model.addAttribute("statuslist", Status.values());

                redirectAttributes.addFlashAttribute("message", pic.getOriginalFilename() + " => " + e.getMessage());
                return "redirect:/services/index";
            }
        } else if (pic.isEmpty() && ourservices.getId() != null) {

            Ourservices ourservicess = ourservicesRepository.getOne(ourservices.getId());

            ourservices.setImageName(ourservicess.getImageName());

            ourservicesRepository.save(ourservices);

            redirectAttributes.addFlashAttribute("message", "Successfully saved.");

            return "redirect:/services/index";

        } else {
            ourservicesRepository.save(ourservices);
            redirectAttributes.addFlashAttribute("message", "File empty");
            return "redirect:/services/index";
        }
//        newsRepository.save(news);
//        return "redirect:/news/index";
    }

    @RequestMapping("/details/{id}")
    public String create(Model model, @PathVariable Long id, Ourservices ourservices) {

        model.addAttribute("services_details", ourservicesRepository.getOne(id));

        return "services/services_details";

    }

    @RequestMapping("/delete/{id}")

    public String delete(Model model, @PathVariable Long id, Ourservices ourservices, RedirectAttributes redirectAttributes) {

        ourservices = ourservicesRepository.getOne(id);
        File file = new File(properties.getRootPath() + File.separator + ourservices.getImageName());

        file.delete();

        ourservicesRepository.deleteById(id);

        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");

        return "redirect:/services/index";
    }

}
