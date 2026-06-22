package com.pacientes_service.pacientes_service.client;

import com.pacientes_service.pacientes_service.dto.MedicoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "medico-service", url = "http://localhost:8081")
public interface MedicoClient {
    @GetMapping("/api/v1/medicos/{id}")
    MedicoDTO obtenerDatosDelMedico(@PathVariable("id") Integer id);
}