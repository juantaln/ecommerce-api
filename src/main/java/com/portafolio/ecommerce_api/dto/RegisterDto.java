package com.portafolio.ecommerce_api.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String username;
    private String password;
    private String email;
}