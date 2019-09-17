/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.model;

import com.itgarden.website.module.user.model.Users;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "User cannot be blank.")
    @OneToOne(optional = true)
    private Users userId;

    @Column(name = "name")
    @NotBlank(message = "*Please provide your name")
    private String name;

    @NotBlank(message = "*Please provide your education qualification")
    private String lastEducationQualification;

    private String occupation;

    private String designation;

    private String organization;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull(message = "*Please provide your blood group")
    @ManyToOne(optional = true)
    private BloodGroup bloodGroup;

    @NotBlank(message = "*Please provide your name")
    private String mobile;

    @NotBlank(message = "*Please provide your present addresss")
    private String presentAddress;

    @NotBlank(message = "*Please provide your present city")
    private String presentCity;

    @NotBlank(message = "*Please provide your country")
    private String presentCountry;

    private String facebookUrl;

    private String linkdinUrl;

    private String whatupNo;

    //    /// Audit /// 
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

    /// End Audit //// 
    public Profile() {
    }

    public Profile(Long id, Users userId, String name, String lastEducationQualification, String occupation, String designation, String organization, Gender gender, BloodGroup bloodGroup, String mobile, String presentAddress, String presentCity, String presentCountry, String facebookUrl, String linkdinUrl, String whatupNo, String createdBy, LocalDateTime created, String modifiedBy, LocalDateTime modified) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.lastEducationQualification = lastEducationQualification;
        this.occupation = occupation;
        this.designation = designation;
        this.organization = organization;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.mobile = mobile;
        this.presentAddress = presentAddress;
        this.presentCity = presentCity;
        this.presentCountry = presentCountry;
        this.facebookUrl = facebookUrl;
        this.linkdinUrl = linkdinUrl;
        this.whatupNo = whatupNo;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastEducationQualification() {
        return lastEducationQualification;
    }

    public void setLastEducationQualification(String lastEducationQualification) {
        this.lastEducationQualification = lastEducationQualification;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
    }

    public String getPresentCity() {
        return presentCity;
    }

    public void setPresentCity(String presentCity) {
        this.presentCity = presentCity;
    }

    public String getPresentCountry() {
        return presentCountry;
    }

    public void setPresentCountry(String presentCountry) {
        this.presentCountry = presentCountry;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getLinkdinUrl() {
        return linkdinUrl;
    }

    public void setLinkdinUrl(String linkdinUrl) {
        this.linkdinUrl = linkdinUrl;
    }

    public String getWhatupNo() {
        return whatupNo;
    }

    public void setWhatupNo(String whatupNo) {
        this.whatupNo = whatupNo;
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

}
