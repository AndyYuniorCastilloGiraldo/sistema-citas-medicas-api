package com.andycg.citas.dto;

import lombok.Data;

@Data
public class LoginResponse {

    private String token;
    private String username;
    private String rol;

    
}