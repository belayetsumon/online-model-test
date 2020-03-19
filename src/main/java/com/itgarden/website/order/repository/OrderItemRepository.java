/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.order.repository;

import com.itgarden.website.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author User
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
}
