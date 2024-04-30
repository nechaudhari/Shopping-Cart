package com.neha.ShoppingCart.dto;

import com.neha.ShoppingCart.entity.CartItems;
import com.neha.ShoppingCart.entity.User;
import com.neha.ShoppingCart.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderDescription;

    private Date date;

    private Long amount;

    private String address;

    private String payment;

    private OrderStatus orderStatus;

    private Long totalAmount;

    private Long discount;

    private UUID trackingId;

    private String userName;

    private String couponName;

    private List<CartItemsDto> cartItems;

}
