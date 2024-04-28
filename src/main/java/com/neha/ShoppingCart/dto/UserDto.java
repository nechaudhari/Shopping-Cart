package com.neha.ShoppingCart.dto;

import com.neha.ShoppingCart.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String email;
    private String name;
    private UserRole userRole;
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDto(Long id, String email, String name, UserRole userRole, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.userRole = userRole;
        this.password = password;
    }

    public UserDto() {
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", userRole=" + userRole +
                ", password='" + password + '\'' +
                '}';
    }
}
