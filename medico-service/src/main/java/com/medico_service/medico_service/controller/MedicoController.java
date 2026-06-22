package com.medico_service.medico_service.controller;

import com.medico_service.medico_service.dto.MedicoDTO;
import com.medico_service.medico_service.model.Medico;
import com.medico_service.medico_service.service.CargaMasivaService;
import com.medico_service.medico_service.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medicos")
@CrossOrigin(origins = "*")
@Tag(name = "Médicos", description = "Operaciones relacionadas con la gestión de médicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private CargaMasivaService cargaMasivaService;

    // Endpoint para Carga Masiva
    @PostMapping("/masivo")
    @Operation(summary = "Carga masiva de médicos", description = "Permite registrar múltiples médicos al mismo tiempo enviando una lista en formato JSON.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Carga masiva procesada con éxito"),
            @ApiResponse(responseCode = "500", description = "Error interno en el servidor durante la carga masiva")
    })
    public ResponseEntity<String> cargaMasiva(@RequestBody List<MedicoDTO> medicosDto) {
        try {
            cargaMasivaService.procesarCarga(medicosDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Carga masiva de " + medicosDto.size() + " médicos procesada con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en la carga masiva: " + e.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Obtener todos los médicos", description = "Retorna una lista con todos los médicos registrados en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de médicos obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay médicos registrados")
    })
    public ResponseEntity<List<Medico>> listar() {
        List<Medico> medicos = medicoService.findAll();
        if (medicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(medicos);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo médico", description = "Registra un nuevo médico en la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Médico creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Medico.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta (datos inválidos)")
    })
    public ResponseEntity<Medico> guardar(@RequestBody Medico medico) {
        Medico medicoNuevo = medicoService.save(medico);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicoNuevo);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un médico por ID", description = "Busca y retorna los datos de un médico específico mediante su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Médico encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Medico.class))),
            @ApiResponse(responseCode = "404", description = "Médico no encontrado")
    })
    public ResponseEntity<Medico> buscar(@PathVariable Integer id) {
        Medico medico = medicoService.findById(id);
        if (medico != null) {
            return ResponseEntity.ok(medico);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un médico existente", description = "Modifica los datos de un médico ya registrado usando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Médico actualizado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Medico.class))),
            @ApiResponse(responseCode = "404", description = "Médico no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Medico> actualizar(@PathVariable Integer id, @RequestBody Medico medico) {
        try {
            Medico med = medicoService.findById(id);
            if (med == null) return ResponseEntity.notFound().build();

            med.setId(id);
            med.setRut(medico.getRut());
            med.setNombre(medico.getNombre());
            med.setApellido(medico.getApellido());
            med.setEspecialidad(medico.getEspecialidad());
            med.setNumeroRegistro(medico.getNumeroRegistro());
            med.setEmail(medico.getEmail());
            med.setTelefono(medico.getTelefono());

            medicoService.save(med);
            return ResponseEntity.ok(med);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un médico", description = "Elimina un médico de la base de datos usando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Médico eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Médico no encontrado")
    })
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            medicoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}