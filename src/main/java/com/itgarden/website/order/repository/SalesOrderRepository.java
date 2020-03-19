/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itgarden.website.order.repository;

import com.itgarden.website.module.user.model.Users;
import com.itgarden.website.order.model.OrderItem;
import com.itgarden.website.order.model.OrderStatus;
import com.itgarden.website.order.model.SalesOrder;
import java.util.List;
import javax.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author User
 */
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
    
    List<SalesOrder> findByCustomer(Users users);
    
    List<SalesOrder> findByCustomerAndStatusOrderByIdDesc(Users users,OrderStatus status);
    
    SalesOrder findFirstByOrderByIdDesc();   
    
    
}
