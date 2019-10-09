/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.exam.ripository;

import com.itgarden.website.exam.model.Exam;
import com.itgarden.website.model.Productsubcategory;
import com.itgarden.website.module.user.model.Users;
import com.itgarden.website.order.model.OrderStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author User
 */
public interface ExamRepository extends JpaRepository<Exam, Long> {
    
    List<Exam> findByProductsubcategoryOrderByIdDesc(Productsubcategory productsubcategory);
    
    List<Exam> findByUserIdAndSalesOrderStatus(Users userid,OrderStatus status);
    List<Exam> findByUserIdOrderByIdDesc(Users userid);

}
