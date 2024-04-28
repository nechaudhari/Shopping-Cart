package com.neha.ShoppingCart.services.admin.adminproduct;

import com.neha.ShoppingCart.dto.ProductDto;

import java.io.IOException;
import java.util.List;

public interface AdminProductService {

    ProductDto addProduct(ProductDto productDto) throws IOException;

    List<ProductDto> getAllProducts();
}
