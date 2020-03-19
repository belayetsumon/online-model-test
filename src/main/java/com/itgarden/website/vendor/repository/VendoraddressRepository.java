/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.vendor.repository;

import com.itgarden.website.vendor.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author User
 */
public interface VendoraddressRepository extends JpaRepository<Address, Long> {
    
    
    
}
