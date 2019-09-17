/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.ripository;

import com.itgarden.website.model.Testimonial;
import com.itgarden.website.model.enumvalue.Status;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author User
 */
public interface TestimonialRepository extends JpaRepository<Testimonial, Long> {
  
    List<Testimonial> findByStatusOrderByIdDesc(Status status,Pageable pageable);
}
