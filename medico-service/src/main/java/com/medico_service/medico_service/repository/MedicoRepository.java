package com.medico_service.medico_service.repository;

import com.medico_service.medico_service.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Integer> {
    // Aquí puedes añadir métodos de búsqueda personalizados si los necesitas, por ejemplo:
    // Optional<Medico> findByRut(String rut);
}