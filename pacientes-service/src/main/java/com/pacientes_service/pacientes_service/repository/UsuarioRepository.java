package com.pacientes_service.pacientes_service.repository;

import com.pacientes_service.pacientes_service.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Spring implementa este método automáticamente
    Optional<Usuario> findByUsername(String username);
}