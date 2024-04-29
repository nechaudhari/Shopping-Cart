package com.neha.ShoppingCart.services.Customer.cart;

import com.neha.ShoppingCart.dto.AddProductInCartDto;
import org.springframework.http.ResponseEntity;

public interface CartService {

    ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto);
}
