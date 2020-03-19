/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.exam.ripository;

import com.itgarden.website.exam.model.Exam;
import com.itgarden.website.model.Productsubcategory;
import com.itgarden.website.model.enumvalue.Status;
import com.itgarden.website.module.user.model.Users;
import com.itgarden.website.order.model.OrderStatus;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author User
 */
public interface ExamRepository extends JpaRepository<Exam, Long> {

    List<Exam> findByProductsubcategoryOrderByIdDesc(Productsubcategory productsubcategory);

    List<Exam> findByUserIdOrderByIdDesc(Users userid);
    
    List<Exam> findByUserIdOrderByIdDesc(Users userid,Pageable pageable);
    
    

    List<Exam> findByUserIdAndStatusOrderByIdDesc(Users userid, Status status);

    List<Exam> findByStatusOrderByIdDesc(Pageable pageable, Status status);
    
    


     List<Exam> findByUserIdAndOrderItemSalesOrderStatus(Pageable pageable, Users userId, OrderStatus orderStatus);

}
