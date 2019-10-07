/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.controller;

import com.itgarden.website.exam.model.Exam;
import com.itgarden.website.exam.ripository.ExamRepository;
import com.itgarden.website.model.Contact;
import com.itgarden.website.model.Gallery;
import com.itgarden.website.model.News;
import com.itgarden.website.model.Ourproduct;
import com.itgarden.website.model.Productcategory;
import com.itgarden.website.model.Productsubcategory;
import com.itgarden.website.module.user.model.Role;
import com.itgarden.website.module.user.model.Status;
import com.itgarden.website.module.user.model.Users;
import com.itgarden.website.module.user.ripository.RoleRepository;
import com.itgarden.website.module.user.ripository.UsersRepository;
import com.itgarden.website.ripository.BatchRepository;
import com.itgarden.website.ripository.ContactRepository;
import com.itgarden.website.ripository.FaqRepository;
import com.itgarden.website.ripository.GalleryRepository;
import com.itgarden.website.ripository.ImageGalleryRepository;
import com.itgarden.website.ripository.NewsRepository;
import com.itgarden.website.ripository.OurclientsRepository;
import com.itgarden.website.ripository.OurproductRepository;
import com.itgarden.website.ripository.OurservicesRepository;
import com.itgarden.website.ripository.ProductcategoryRepository;
import com.itgarden.website.ripository.ProductsubcategoryRepository;
import com.itgarden.website.ripository.ProfileRepository;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author User
 */
@Controller
@RequestMapping("/front-view")
public class FrontController {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    BatchRepository batchRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    GalleryRepository galleryRepository;

    @Autowired
    ImageGalleryRepository imageGalleryRepository;

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    ProductcategoryRepository productcategoryRepository;

    @Autowired
    OurproductRepository ourproductRepository;

    @Autowired
    OurservicesRepository ourservicesRepository;

    @Autowired
    OurclientsRepository ourclientsRepository;

    @Autowired
    FaqRepository faqRepository;

    @Autowired
    ProductsubcategoryRepository productsubcategoryRepository;

    @Autowired
    ExamRepository examRepository;

    @Autowired
    RoleRepository roleRepository;

    @RequestMapping("/about-us")
    public String aboutUs(Model model) {
        model.addAttribute("attribute", "value");
        return "frontview/aboutUs";
    }

//    @RequestMapping("/mission-vision")
//    public String missionVvision(Model model) {
//        model.addAttribute("attribute", "value");
//        return "frontview/mission-vision";
//    }
//    @RequestMapping("/ceo-message")
//    public String ceomessage(Model model) {
//        model.addAttribute("attribute", "value");
//        return "frontview/ceo-message";
//    }
//    @RequestMapping("/agent-division")
//    public String agentdivision(Model model) {
//        model.addAttribute("attribute", "value");
//        return "frontview/agent_division";
//    }
//    @RequestMapping("/trade-division")
//    public String tradedivision(Model model) {
//        model.addAttribute("attribute", "value");
//        return "frontview/trade_division";
//    }
//    @RequestMapping("/corporate-value")
//    public String corporatevalue(Model model) {
//        model.addAttribute("attribute", "value");
//        return "frontview/corporate_value";
//    }
//    @RequestMapping("/code-conduct")
//    public String codeconduct(Model model) {
//        model.addAttribute("attribute", "value");
//        return "frontview/code_conduct";
//    }
//    @RequestMapping("/executive-committee")
//    public String executiveCommittee(Model model) {
//        model.addAttribute("attribute", "value");
//        return "frontview/executive-committee";
//    }
//    @RequestMapping("/constitution")
//    public String constitution(Model model) {
//        model.addAttribute("attribute", "value");
//        return "frontview/constitution";
//    }
//    @RequestMapping("/advisory-council")
//    public String advisoryCouncil(Model model) {
//        model.addAttribute("attribute", "value");
//        return "frontview/advisory-council";
//    }
//    @RequestMapping("/all-member")
//    public String allmember(Model model) {
//        model.addAttribute("allmember", usersRepository.findByStatus(Status.Active));
//        return "frontview/all-member";
//    }
//    @RequestMapping("/member-search")
//    public String membersearch(Model model) {
//        model.addAttribute("attribute", "value");
//        return "frontview/all-search";
//    }
    @RequestMapping("/member-login")
    public String memberlogin(Model model) {
        model.addAttribute("attribute", "value");
        return "frontview/member-login";
    }

    @RequestMapping("/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("attribute", "value");
        return "frontview/forgot-password";
    }

//    @RequestMapping("/batch-modaretor")
//    public String batchmodaretor(Model model) {
//        model.addAttribute("attribute", "value");
//        return "frontview/batch-modaretor";
//    }
//    @RequestMapping("/resourses")
//    public String resourses(Model model) {
//        model.addAttribute("attribute", "value");
//        return "frontview/resourses";
//    }
//    @RequestMapping("/wholesale")
//    public String wholesale(Model model) {
//        model.addAttribute("attribute", "value");
//        return "frontview/wholesale";
//    }
//    @RequestMapping("/product")
//    public String product(Model model) {
//        model.addAttribute("productlist", ourproductRepository.findByStatusOrderByIdDesc(com.itgarden.website.model.enumvalue.Status.Active));
//        model.addAttribute("productcategorylist", productcategoryRepository.findByStatusAndOurproductStatus(com.itgarden.website.model.enumvalue.Status.Active, com.itgarden.website.model.enumvalue.Status.Active));
//        return "frontview/product";
//    }
    @RequestMapping("/product-by-category/{prodcatid}")
    public String productByCategory(Model model, @PathVariable long prodcatid, Productcategory productcategory) {

        productcategory.setId(prodcatid);

        model.addAttribute("productlist", ourproductRepository.findByProductcategoryOrderByIdDesc(productcategory));

        model.addAttribute("productcategorylist", productcategoryRepository.findByStatusAndOurproductStatus(com.itgarden.website.model.enumvalue.Status.Active, com.itgarden.website.model.enumvalue.Status.Active));

        model.addAttribute("productcategoryname", productcategoryRepository.getOne(prodcatid));

        return "frontview/product-by-category";
    }

    @RequestMapping("/single-product/{prodid}")
    public String single_product(Model model, @PathVariable long prodid, Ourproduct ourproduct) {

        model.addAttribute("product_details", ourproductRepository.getOne(prodid));

        return "frontview/single-product";
    }

    @RequestMapping("/news-events")
    public String newsEvents(Model model, @RequestParam(defaultValue = "0") int page) {

        Pageable pageable;
        pageable = new PageRequest(page, 5, Sort.by("id").descending());

        Page<News> pagelist = newsRepository.findAll(pageable);

        model.addAttribute("pagelist", pagelist);

        return "frontview/newsEvents";
    }

    @RequestMapping("/news-details/{newsid}")
    public String newsdetails(Model model, @PathVariable Long newsid) {

        model.addAttribute("singlenews", newsRepository.getOne(newsid));
        return "frontview/newsdetails";
    }

    @RequestMapping("/gallery")
    public String gallery(Model model) {
        model.addAttribute("gallerylist", galleryRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        return "frontview/gallery";
    }

    @RequestMapping("/galleryimage/{galleryid}")
    public String galleryimage(Model model, @PathVariable Long galleryid) {

        Gallery gallery = new Gallery();
        gallery.setId(galleryid);
        model.addAttribute("galleryimagelist", imageGalleryRepository.findByGalleryOrderByIdDesc(gallery));
        return "frontview/galleryimage";
    }

    @RequestMapping("/donations")
    public String donations(Model model) {
        model.addAttribute("attribute", "value");
        return "frontview/donations";
    }

    @RequestMapping("/services")
    public String services(Model model) {
        model.addAttribute("servicelist", ourservicesRepository.findByStatusOrderByIdDesc(com.itgarden.website.model.enumvalue.Status.Active));
        return "frontview/services";
    }

    @RequestMapping("/services-details")
    public String services_details(Model model) {
        model.addAttribute("servicelist", ourservicesRepository.findByStatusOrderByIdDesc(com.itgarden.website.model.enumvalue.Status.Active));
        return "frontview/services_details";
    }

    @RequestMapping("/clients")
    public String clients(Model model) {
        model.addAttribute("clientslist", ourclientsRepository.findByStatusOrderByIdDesc(com.itgarden.website.model.enumvalue.Status.Active));
        return "frontview/clients";
    }

    @RequestMapping("/jobs")
    public String jobs(Model model) {
        model.addAttribute("attribute", "value");
        return "frontview/jobs";
    }

    @RequestMapping("/testimonial")
    public String testimonial(Model model) {
        model.addAttribute("attribute", "value");
        return "frontview/testimonial";
    }

    @RequestMapping("/faq")
    public String faq(Model model) {
        model.addAttribute("faqlist", faqRepository.findByStatusOrderByIdDesc(com.itgarden.website.model.enumvalue.Status.Active));
        return "frontview/faq";
    }

    @RequestMapping("/contactUs")
    public String contactUs(Model model) {
        model.addAttribute("attribute", "value");
        return "frontview/contactUs";
    }

    @RequestMapping("/home-contact-save")
    public String homecontactsave(Model model, @Valid Contact contact, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        model.addAttribute("attribute", "value");

        if (bindingResult.hasErrors()) {
            return "welcome/welcome";
        }
        contactRepository.save(contact);

        redirectAttributes.addFlashAttribute("message", "Your Message Successfully ");

        return "redirect:/";
    }

    //  Model test Start Here 
    @RequestMapping("/sub-category-by-category/{catid}")
    public String by_category(Model model, @PathVariable Long catid, Productsubcategory productsubcategory) {
        Productcategory productcategory = new Productcategory();

        productcategory.setId(catid);

        model.addAttribute("subcategorylist", productsubcategoryRepository.findByProductcategory(productcategory));
        model.addAttribute("category", productcategoryRepository.getOne(catid));
        model.addAttribute("categorylist", productcategoryRepository.findAll());

        return "frontview/sub-category-by-category";
    }

    @RequestMapping("/exam-by-sub-category/{subcatid}")
    public String exam_by_subcategory(Model model, @PathVariable Long subcatid, Productsubcategory productsubcategory) {

        productsubcategory.setId(subcatid);

        model.addAttribute("subcategory", productsubcategoryRepository.getOne(subcatid));

        model.addAttribute("examlist", examRepository.findByProductsubcategoryOrderByIdDesc(productsubcategory));

        // model.addAttribute("category", productcategoryRepository.getOne(catid));
        model.addAttribute("categorylist", productcategoryRepository.findAll());

        return "frontview/exam-by-sub-category";
    }

    @RequestMapping("/exam-details/{examid}")
    public String exam_details(Model model, @PathVariable Long examid, Exam exam) {

        model.addAttribute("exam", examRepository.getOne(examid));

        return "frontview/exam-details";
    }

    @RequestMapping("/all-exam")
    public String allexam(Model model, Exam exam) {

        model.addAttribute("categorylist", productcategoryRepository.findAll());

        model.addAttribute("examlist", examRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));

        return "frontview/all-exam";
    }

    @RequestMapping("/front-registration")
    public String studentRegistration(Model model, Users users) {

        Role instructor = roleRepository.findBySlug("instructor");

        model.addAttribute("instructor", instructor);

        Role customer = roleRepository.findBySlug("customer");

        model.addAttribute("customer", customer);

        return "frontview/front-registration";
    }

  
}
