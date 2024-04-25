package com.neha.ShoppingCart.controller;

import com.neha.ShoppingCart.dto.AuthenticationRequest;
import com.neha.ShoppingCart.dto.SignupRequest;
import com.neha.ShoppingCart.dto.UserDto;
import com.neha.ShoppingCart.entity.User;
import com.neha.ShoppingCart.repository.UserRepository;
import com.neha.ShoppingCart.services.auth.AuthService;
import com.neha.ShoppingCart.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping
@CrossOrigin(origins = {"https://localhost:8080"})
public class AuthController {

    private static final String TOKEN_PREFIX = "Bearer" ;
    private static final String HEADER_STRING = "Authorization" ;
    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final AuthService authService;

    @PostMapping("/authenticate")
    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
                                          HttpServletResponse response) throws IOException, JSONException {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()));
        } catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect Username or Password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        if(optionalUser.isPresent()){
            response.getWriter().write(new JSONObject()
                    .put("userId", optionalUser.get().getId())
                    .put("role", optionalUser.get().getRole())
                    .toString()
            );

            response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
        }
    }
    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
        if(authService.hasUserWithEmail(signupRequest.getEmail())){
            return new ResponseEntity<>("User already exist", HttpStatus.NOT_ACCEPTABLE);
        }

        UserDto userDto = authService.createUser(signupRequest);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
