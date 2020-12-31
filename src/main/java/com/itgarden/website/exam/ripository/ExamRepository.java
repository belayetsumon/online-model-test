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
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author User
 */
public interface ExamRepository extends JpaRepository<Exam, Long> {

    List<Exam> findByProductsubcategoryOrderByIdDesc(Productsubcategory productsubcategory);

    List<Exam> findByUserIdOrderByIdDesc(Users userid);

    List<Exam> findByUserIdOrderByIdDesc(Users userid, Pageable pageable);

    List<Exam> findByUserIdAndStatusOrderByIdDesc(Users userid, Status status);

    List<Exam> findByStatusOrderByIdDesc(Pageable pageable, Status status);

    List<Exam> findByUserIdAndOrderItemSalesOrderStatus(Pageable pageable, Users userId, OrderStatus orderStatus);

    //Bestseller
    @Query(
            value = "SELECT e.*,COUNT(o.exam_id) AS orderitems FROM exam e "
            + "INNER JOIN order_item o "
            + "ON  e.id=o.exam_id "
            + "GROUP BY  e.id "
            + " ORDER  BY  orderitems  DESC ",
            nativeQuery = true
    )
    List<Exam> findByBestSeller(Pageable pageable);
    
        //Top Rated
    
        @Query(
            value = "SELECT e.*,SUM(r.ratenumber) AS toprate FROM exam e "
            + "INNER JOIN rate r "
            + "ON  e.id=r.exam_id "
            + "GROUP BY  e.id "
            + " ORDER  BY  toprate  DESC ",
            nativeQuery = true
    )
    List<Exam> findByTopRated(Pageable pageable);
    
    
    
      //Best Instructor
    @Query(
            value = "SELECT e.*,COUNT(o.exam_id) AS orderitems FROM exam e "
            + "INNER JOIN order_item o "
            + "ON  e.id=o.exam_id "
            + "GROUP BY  e.user_id_id "
            + " ORDER  BY  orderitems  DESC ",
            nativeQuery = true
    )
    List<Exam> findByBestInstructor(Pageable pageable);

}
