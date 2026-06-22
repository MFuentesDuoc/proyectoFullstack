package com.medico_service.medico_service.service;

import com.medico_service.medico_service.dto.MedicoDTO;
import com.medico_service.medico_service.model.Medico;
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
    public void procesarCarga(List<MedicoDTO> listaDto) {
        // Coincide con el batch_size de tu application.properties
        int batchSize = 50;

        for (int i = 0; i < listaDto.size(); i++) {
            MedicoDTO dto = listaDto.get(i);

            // Creamos la instancia de la entidad Medico
            Medico medico = new Medico();

            // Mapeamos los campos según la nueva estructura de Medico
            medico.setRut(dto.getRut());
            medico.setNombre(dto.getNombre());
            medico.setApellido(dto.getApellido());
            medico.setEspecialidad(dto.getEspecialidad());
            medico.setNumeroRegistro(dto.getNumeroRegistro());
            medico.setEmail(dto.getEmail());
            medico.setTelefono(dto.getTelefono());

            // Persistimos el objeto medico
            entityManager.persist(medico);

            // Cada 50 registros, sincronizamos con la base de datos y liberamos memoria
            if (i > 0 && i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }

        // Sincronización final
        entityManager.flush();
        entityManager.clear();
    }
}