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
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author User
 */
public interface TestRepository extends JpaRepository<Test, Long> {
    
    List<Test> findByExamOrderByIdDesc(Exam exam);
    List<Test> findByExamAndUserIdOrderByIdDesc(Exam exam,Users users);
}
