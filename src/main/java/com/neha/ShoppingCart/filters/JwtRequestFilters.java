package com.neha.ShoppingCart.filters;

import jakarta.servlet.ServletException;

import java.io.IOException;

public interface JwtRequestFilters {
    void doFilterInternal(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response,
                          javax.servlet.FilterChain filterChain) throws IOException, ServletException, javax.servlet.ServletException;
}
