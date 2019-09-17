/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.controller;

import com.itgarden.website.model.Ourproduct;
import com.itgarden.website.model.enumvalue.Status;
import com.itgarden.website.ripository.OurproductRepository;
import com.itgarden.website.ripository.ProductcategoryRepository;
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
@RequestMapping("/ourproduct")
public class OurproductController {

    @Autowired
    StorageProperties properties;

    @Autowired
    ProductcategoryRepository productcategoryRepository;

    @Autowired
    OurproductRepository ourproductRepository;

    @RequestMapping(value = {"", "/", "/index"})
    public String index(Model model) {
        model.addAttribute("productlist", ourproductRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        return "catalog/product/index";
    }

    @RequestMapping("/create")
    public String create(Model model, Ourproduct ourproduct) {
        model.addAttribute("statuslist", Status.values());
        model.addAttribute("productcategorylist", productcategoryRepository.findByStatus(Status.Active));
        return "catalog/product/add";
    }

    @RequestMapping("/save")
    public String create(Model model, @Valid Ourproduct ourproduct, BindingResult bindingResult, RedirectAttributes redirectAttributes,
            @RequestParam("pic") MultipartFile pic
    ) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("statuslist", Status.values());
            model.addAttribute("productcategorylist", productcategoryRepository.findByStatus(Status.Active));
            return "catalog/product/add";
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

                ourproduct.setImageName(filename);

                ourproductRepository.save(ourproduct);

                redirectAttributes.addFlashAttribute("message", "Successfully saved.");
                return "redirect:/ourproduct/index";
            } catch (Exception e) {

                model.addAttribute("statuslist", Status.values());
                model.addAttribute("productcategorylist", productcategoryRepository.findByStatus(Status.Active));

                redirectAttributes.addFlashAttribute("message", pic.getOriginalFilename() + " => " + e.getMessage());
                return "redirect:/ourproduct/index";
            }
        } else if (pic.isEmpty() && ourproduct.getId() != null) {

            Ourproduct ourproducts = ourproductRepository.getOne(ourproduct.getId());

            ourproduct.setImageName(ourproducts.getImageName());

            ourproductRepository.save(ourproduct);

            redirectAttributes.addFlashAttribute("message", "Successfully saved.");

            return "redirect:/ourproduct/index";

        } else {
            ourproductRepository.save(ourproduct);
            redirectAttributes.addFlashAttribute("message", "File empty");
            return "redirect:/ourproduct/index";
        }
//        newsRepository.save(news);
//        return "redirect:/news/index";
    }

    @RequestMapping("/details/{id}")
    public String create(Model model, @PathVariable Long id, Ourproduct ourproduct) {

        model.addAttribute("product_details", ourproductRepository.getOne(id));

        return "catalog/product/product_details";

    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, Ourproduct ourproduct) {
        model.addAttribute("ourproduct", ourproductRepository.getOne(id));
        model.addAttribute("statuslist", Status.values());
        model.addAttribute("productcategorylist", productcategoryRepository.findByStatus(Status.Active));
        return "catalog/product/add";
    }

    @RequestMapping("/delete/{id}")

    public String delete(Model model, @PathVariable Long id, Ourproduct ourproduct, RedirectAttributes redirectAttributes) {

        ourproduct = ourproductRepository.getOne(id);
        File file = new File(properties.getRootPath() + File.separator + ourproduct.getImageName());

        file.delete();

        ourproductRepository.deleteById(id);

        redirectAttributes.addFlashAttribute("message", "Deleted successfully.");

        return "redirect:/ourproduct/index";
    }

}
