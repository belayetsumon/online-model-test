/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.model.cart;

import com.itgarden.website.model.Ourproduct;
import java.io.Serializable;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author User
 */

@Scope("session")
public class CartItem implements Serializable {

    private Ourproduct ourproduct;

    private int quantity;

    public CartItem(Ourproduct ourproduct, int quantity) {
        this.ourproduct = ourproduct;
        this.quantity = quantity;
    }

    public CartItem() {
    }

    public Ourproduct getOurproduct() {
        return ourproduct;
    }

    public void setOurproduct(Ourproduct ourproduct) {
        this.ourproduct = ourproduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }  
}
