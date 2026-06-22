package com.pacientes_service.pacientes_service.repository;

import com.pacientes_service.pacientes_service.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
}
