package com.pacientes_service.pacientes_service.service;

import com.pacientes_service.pacientes_service.dto.PacienteDTO;
import com.pacientes_service.pacientes_service.model.Paciente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CargaMasivaService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void procesarCarga(List<PacienteDTO> listaDto) {
        // Coincide con el batch_size de tu application.properties
        int batchSize = 50;

        for (int i = 0; i < listaDto.size(); i++) {
            PacienteDTO dto = listaDto.get(i);

            // Creamos la instancia de la entidad Paciente
            Paciente paciente = new Paciente();

            // Mapeamos los campos
            paciente.setRut(dto.getRut());
            paciente.setNombre(dto.getNombre());
            paciente.setApellido(dto.getApellido());
            paciente.setFechaNacimiento(dto.getFechaNacimiento());
            paciente.setEmail(dto.getEmail());
            paciente.setTelefono(dto.getTelefono());
            paciente.setDireccion(dto.getDireccion());

            // Mapeamos el ID del médico que viene desde el JSON de Postman
            paciente.setMedicoId(dto.getMedicoId());

            // Persistimos el objeto paciente (Debe estar solo una vez)
            entityManager.persist(paciente);

            // Cada 50 registros, sincronizamos con la base de datos y liberamos memoria
            if (i > 0 && i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }

        // Sincronización final por si el total no es múltiplo exacto de 50
        entityManager.flush();
        entityManager.clear();
    }
}