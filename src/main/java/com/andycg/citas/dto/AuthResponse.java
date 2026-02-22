package com.andycg.citas.dto;




public record AuthResponse(
        String token,
        String username,
        String rol) {
}
