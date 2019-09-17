/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.controller;

import com.itgarden.website.model.Ourclients;
import com.itgarden.website.model.enumvalue.Status;
import com.itgarden.website.ripository.OurclientsRepository;
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
@RequestMapping("/clients")
public class OurclientsController {

    @Autowired
    StorageProperties properties;

    @Autowired
    OurclientsRepository ourclientsRepository;

    @RequestMapping(value = {"", "/", "/index"})
    public String index(Model model) {
        model.addAttribute("clientlist", ourclientsRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        return "clients/index";
    }

    @RequestMapping("/create")
    public String create(Model model, Ourclients ourclients) {
        model.addAttribute("statuslist", Status.values());
        return "clients/add";
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, Ourclients ourclients) {

        model.addAttribute("ourclients", ourclientsRepository.getOne(id));
        model.addAttribute("statuslist", Status.values());
        return "clients/add";
    }

    @RequestMapping("/save")
    public String create(Model model, @Valid Ourclients ourclients, BindingResult bindingResult, RedirectAttributes redirectAttributes,
            @RequestParam("pic") MultipartFile pic
    ) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("statuslist", Status.values());
            return "clients/add";
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

                Thumbnails.of(originalImage).forceSize(200, 150).toFile(serverFile);

                model.addAttribute("message", "You successfully uploaded");

                ourclients.setImageName(filename);

                ourclientsRepository.save(ourclients);

                redirectAttributes.addFlashAttribute("message", "Successfully saved.");
                return "redirect:/clients/index";
            } catch (Exception e) {

                model.addAttribute("statuslist", Status.values());

                redirectAttributes.addFlashAttribute("message", pic.getOriginalFilename() + " => " + e.getMessage());
                return "redirect:/clients/index";
            }
        } else if (pic.isEmpty() && ourclients.getId() != null) {

            Ourclients ourclientss = ourclientsRepository.getOne(ourclients.getId());

            ourclients.setImageName(ourclientss.getImageName());

            ourclientsRepository.save(ourclients);

            redirectAttributes.addFlashAttribute("message", "Successfully saved.");

            return "redirect:/clients/index";

        } else {
            ourclientsRepository.save(ourclients);
            redirectAttributes.addFlashAttribute("message", "File empty");
            return "redirect:/clients/index";
        }
//        newsRepository.save(news);
//        return "redirect:/news/index";
    }

    @RequestMapping("/details/{id}")
    public String create(Model model, @PathVariable Long id, Ourclients ourclients) {

        model.addAttribute("clients_details", ourclientsRepository.getOne(id));

        return "clients/clients_details";

    }

    @RequestMapping("/delete/{id}")

    public String delete(Model model, @PathVariable Long id, Ourclients ourclients, RedirectAttributes redirectAttributes) {

        ourclients = ourclientsRepository.getOne(id);
        File file = new File(properties.getRootPath() + File.separator + ourclients.getImageName());

        file.delete();

        ourclientsRepository.deleteById(id);

        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");

        return "redirect:/clients/index";
    }

}
