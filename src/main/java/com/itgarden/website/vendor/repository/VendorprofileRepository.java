/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.vendor.repository;

import com.itgarden.website.module.user.model.Users;
import com.itgarden.website.vendor.model.Vendorprofile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author User
 */
public interface VendorprofileRepository extends JpaRepository<Vendorprofile, Long> {

    Vendorprofile findByUserId(Users users);

}
