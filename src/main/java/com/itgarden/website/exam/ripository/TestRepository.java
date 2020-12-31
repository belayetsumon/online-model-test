/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.exam.ripository;

import com.itgarden.website.exam.model.Exam;
import com.itgarden.website.exam.model.Test;
import com.itgarden.website.module.user.model.Users;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author User
 */
public interface TestRepository extends JpaRepository<Test, Long> {

    List<Test> findByExamOrderByIdDesc(Exam exam);

    List<Test> findByExamAndUserIdOrderByIdDesc(Exam exam, Users users);

    //topper student
    @Query(
            value = "SELECT t.*,SUM(m.value) AS topmark FROM test t "
            + "INNER JOIN mark m "
            + "ON  t.id=m.test_id "
            + "GROUP BY  t.id "
            + " ORDER  BY  topmark  DESC ",
            nativeQuery = true
    )
            
    List<Test> findByTopperStudent(Pageable pageable);
}
