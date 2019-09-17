/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.ripository;

import com.itgarden.website.model.Ourclients;
import com.itgarden.website.model.enumvalue.Status;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author User
 */
public interface OurclientsRepository extends JpaRepository<Ourclients, Long> {
    
     List<Ourclients> findByStatusOrderByIdDesc(Status status);
}
