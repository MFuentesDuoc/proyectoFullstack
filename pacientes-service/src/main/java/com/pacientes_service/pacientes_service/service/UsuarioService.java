package com.pacientes_service.pacientes_service.service;

import com.pacientes_service.pacientes_service.model.Usuario;
import com.pacientes_service.pacientes_service.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Buscar usuario por username
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));
    }

    // Guardar un usuario nuevo (con contraseña encriptada)
    public Usuario save(Usuario usuario) {
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    // Verificar si existe un usuario
    public boolean existsByUsername(String username) {
        return usuarioRepository.findByUsername(username).isPresent();
    }

    // Crear usuario ADMIN automáticamente si no existe
    public void crearAdminSiNoExiste() {
        if (!existsByUsername("admin")) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword("1234");  // Se encriptará automáticamente en save()
            admin.setRol("ADMIN");
            save(admin);
            System.out.println(" Usuario ADMIN creado: admin / 1234");
        }
    }
}