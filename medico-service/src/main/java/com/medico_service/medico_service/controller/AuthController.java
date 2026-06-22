package com.medico_service.medico_service.controller;

import com.medico_service.medico_service.dto.LoginRequestDTO;
import com.medico_service.medico_service.dto.AuthResponseDTO;
import com.medico_service.medico_service.model.Usuario;
import com.medico_service.medico_service.security.JwtService;
import com.medico_service.medico_service.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO request) {

        // Buscar el usuario
        Usuario usuario = usuarioService.findByUsername(request.getUsername());

        // Verificar contraseña
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        // Generar token
        String token = jwtService.generateToken(usuario.getUsername());

        // Devolver respuesta
        return new AuthResponseDTO(token, usuario.getUsername(), usuario.getRol());
    }
}