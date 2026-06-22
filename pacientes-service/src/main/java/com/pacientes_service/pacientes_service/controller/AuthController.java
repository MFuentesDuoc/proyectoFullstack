package com.pacientes_service.pacientes_service.controller;

import com.pacientes_service.pacientes_service.dto.LoginRequestDTO;
import com.pacientes_service.pacientes_service.dto.AuthResponseDTO;
import com.pacientes_service.pacientes_service.model.Usuario;
import com.pacientes_service.pacientes_service.security.JwtService;
import com.pacientes_service.pacientes_service.service.UsuarioService;
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