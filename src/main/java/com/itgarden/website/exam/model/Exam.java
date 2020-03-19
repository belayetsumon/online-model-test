/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.exam.model;

import com.itgarden.website.model.Productsubcategory;
import com.itgarden.website.model.enumvalue.Status;
import com.itgarden.website.module.user.model.Users;
import com.itgarden.website.order.model.OrderItem;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *
 * @author User
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Exam implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = " User cannot be blank.")
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Users userId;

    @NotNull(message = "Please select minimum one sub category")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "exam_productsubcategory",
            joinColumns = @JoinColumn(name = "exam_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "productsubcategory_id", referencedColumnName = "id"))

    private Set<Productsubcategory> productsubcategory;

    @NotBlank(message = "Title  is required.")
    private String title;

    @NotBlank(message = "Slug  is required.")
    private String slug;

    @Lob
    private String subtitle;

    @Lob

    private String syllabus;

    private int orderno;

    @Lob

    private String description;

    private double price;

    private double discount;

    private String imageName;

    @NotNull(message = "Lavel is required.")
    @Enumerated(EnumType.STRING)
    private Lavelstatus level;

    @NotNull(message = "Status is required.")
    @Enumerated(EnumType.STRING)
    private Status status;

    /// Audit /// 
    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime created;

    @LastModifiedBy
    @Column(insertable = false)
    private String modifiedBy;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime modified;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Question> question = new ArrayList<>();

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Test> test = new ArrayList<>();

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<OrderItem> orderItem = new ArrayList<>();

    public Exam() {
    }

    public Exam(Long id, Users userId, Set<Productsubcategory> productsubcategory, String title, String slug, String subtitle, String syllabus, int orderno, String description, double price, double discount, String imageName, Lavelstatus level, Status status, String createdBy, LocalDateTime created, String modifiedBy, LocalDateTime modified) {
        this.id = id;
        this.userId = userId;
        this.productsubcategory = productsubcategory;
        this.title = title;
        this.slug = slug;
        this.subtitle = subtitle;
        this.syllabus = syllabus;
        this.orderno = orderno;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.imageName = imageName;
        this.level = level;
        this.status = status;
        this.createdBy = createdBy;
        this.created = created;
        this.modifiedBy = modifiedBy;
        this.modified = modified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public Set<Productsubcategory> getProductsubcategory() {
        return productsubcategory;
    }

    public void setProductsubcategory(Set<Productsubcategory> productsubcategory) {
        this.productsubcategory = productsubcategory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public int getOrderno() {
        return orderno;
    }

    public void setOrderno(int orderno) {
        this.orderno = orderno;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Lavelstatus getLevel() {
        return level;
    }

    public void setLevel(Lavelstatus level) {
        this.level = level;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public List<Question> getQuestion() {
        return question;
    }

    public void setQuestion(List<Question> question) {
        this.question = question;
    }

    public List<Test> getTest() {
        return test;
    }

    public void setTest(List<Test> test) {
        this.test = test;
    }

    public List<OrderItem> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(List<OrderItem> orderItem) {
        this.orderItem = orderItem;
    }



    
}
