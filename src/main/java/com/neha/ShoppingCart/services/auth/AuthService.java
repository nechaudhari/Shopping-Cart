package com.neha.ShoppingCart.services.auth;

import com.neha.ShoppingCart.dto.SignupRequest;
import com.neha.ShoppingCart.dto.UserDto;

public interface AuthService {

    UserDto createUser(SignupRequest signupRequest);

    Boolean hasUserWithEmail(String email);
}
