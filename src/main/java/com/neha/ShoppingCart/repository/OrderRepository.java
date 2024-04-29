package com.neha.ShoppingCart.repository;

import com.neha.ShoppingCart.entity.Order;
import com.neha.ShoppingCart.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);
}
