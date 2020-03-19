/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.model.cart;

import com.itgarden.website.exam.model.Exam;
import java.io.Serializable;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author User
 */

@Scope("session")
public class CartItem implements Serializable {

    private Exam exam;

    private int quantity;

    public CartItem() {
    }

    public CartItem(Exam exam, int quantity) {
        this.exam = exam;
        this.quantity = quantity;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
