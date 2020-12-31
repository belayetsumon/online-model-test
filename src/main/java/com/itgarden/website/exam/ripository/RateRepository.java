/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.exam.ripository;

import com.itgarden.website.exam.model.Rate;
import com.itgarden.website.module.user.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author User
 */
public interface RateRepository extends JpaRepository<Rate, Long> {

    Page<Rate> findByUserId(Users userid, Pageable pageable);

}
