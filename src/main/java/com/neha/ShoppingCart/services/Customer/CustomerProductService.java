package com.neha.ShoppingCart.services.Customer;

import com.neha.ShoppingCart.dto.ProductDto;

import java.util.List;

public interface CustomerProductService {

     List<ProductDto> getAllProducts();

     List<ProductDto> searchProductByTitle(String title);

}
