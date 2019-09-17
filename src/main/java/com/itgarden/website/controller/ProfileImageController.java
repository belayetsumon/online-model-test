/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.controller;

import com.itgarden.website.model.ProfileImage;
import com.itgarden.website.module.user.model.Users;
import com.itgarden.website.module.user.services.LoggedUserService;
import com.itgarden.website.ripository.ProfileImageRepository;
import com.itgarden.website.services.StorageProperties;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.validation.Valid;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/profileimage")
public class ProfileImageController {

    @Autowired
    ProfileImageRepository profileImageRepository;

    @Autowired
    LoggedUserService loggedUserService;

    @Autowired
    StorageProperties properties;

    @RequestMapping("/create")
    public String add(Model model, ProfileImage profileImage) {

        Users userss = new Users();
        userss.setId(loggedUserService.activeUserid());
        profileImage.setUserId(userss);

        return "frontview/profileimage_add";
    }

    @RequestMapping("/save")
    //@Transactional
    public String save(Model model, @Valid ProfileImage profileImage, BindingResult bindingResult, RedirectAttributes redirectAttributes,
            @RequestParam("pic") MultipartFile pic) {

        if (bindingResult.hasErrors()) {
            Users userss = new Users();
            userss.setId(loggedUserService.activeUserid());
            profileImage.setUserId(userss);
            return "frontview/profileimage_add";
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

                BufferedImage originalImage;
                originalImage = ImageIO.read(pic.getInputStream());

                Thumbnails.of(originalImage)
                        .forceSize(300, 225)
                        .toFile(serverFile);

                model.addAttribute("message", "You successfully uploaded");

                profileImage.setImageName(filename);

                profileImageRepository.save(profileImage);
                return "redirect:/profile/index";
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("message", pic.getOriginalFilename() + " => " + e.getMessage());
                return "redirect:/profile/index";
            }
        } else if (pic.isEmpty() && profileImage.getId() != null) {

            ProfileImage profileImages = profileImageRepository.getOne(profileImage.getId());

            profileImage.setImageName(profileImages.getImageName());
            profileImageRepository.save(profileImage);

            redirectAttributes.addFlashAttribute("message", "Successfully saved.");

            return "redirect:/profile/index";

        } else {
            redirectAttributes.addFlashAttribute("message", "File empty");
            return "redirect:/profile/index";
        }
    }

    @RequestMapping("/delete/{id}")

    public String delete(Model model, @PathVariable Long id, ProfileImage profileImage, RedirectAttributes redirectAttributes) {

        profileImageRepository.deleteById(id);

        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");

        return "redirect:/profile/index";
    }

}
