/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.ripository;

import com.itgarden.website.model.Productcategory;
import com.itgarden.website.model.enumvalue.Status;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author User
 */
public interface ProductcategoryRepository extends JpaRepository<Productcategory, Long> {

    List<Productcategory> findByStatus(Status status);

    List<Productcategory> findByStatusAndOurproductStatus(Status cattatus,Status productstatus);
}
