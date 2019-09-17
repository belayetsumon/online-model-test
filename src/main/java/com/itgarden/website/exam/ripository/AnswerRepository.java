/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.exam.ripository;

import com.itgarden.website.exam.model.Answer;
import com.itgarden.website.exam.model.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author User
 */
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    
    List<Answer>  findByQuestion(Question question);
    
    Answer findByIdAndQuestion(Long id,Question question);
    
}
