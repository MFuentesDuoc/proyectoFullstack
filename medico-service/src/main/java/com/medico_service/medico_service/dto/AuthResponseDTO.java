package com.medico_service.medico_service.dto;

public class AuthResponseDTO {
    private String token;
    private String username;
    private String rol;

    public AuthResponseDTO(String token, String username, String rol) {
        this.token = token;
        this.username = username;
        this.rol = rol;
    }

    // Getters y Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}