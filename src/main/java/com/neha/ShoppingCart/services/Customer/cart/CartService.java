package com.neha.ShoppingCart.services.Customer.cart;

import com.neha.ShoppingCart.dto.AddProductInCartDto;
import com.neha.ShoppingCart.dto.OrderDto;
import org.springframework.http.ResponseEntity;

public interface CartService {

    ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto);

    OrderDto getCartByUserId(Long userId);
}
