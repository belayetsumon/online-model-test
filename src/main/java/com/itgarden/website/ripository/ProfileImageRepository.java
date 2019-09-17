/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.ripository;

import com.itgarden.website.model.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author User
 */
public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
    
}
