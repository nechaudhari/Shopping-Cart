package com.neha.ShoppingCart.services.adminCategory;


import com.neha.ShoppingCart.dto.CategoryDto;
import com.neha.ShoppingCart.entity.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(CategoryDto categoryDto);

    List<Category> getAllCategories();
}
