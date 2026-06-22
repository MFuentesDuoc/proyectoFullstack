package com.pacientes_service.pacientes_service.controller;

import com.pacientes_service.pacientes_service.dto.PacienteDTO;
import com.pacientes_service.pacientes_service.dto.MedicoDTO;
import com.pacientes_service.pacientes_service.model.Paciente;
import com.pacientes_service.pacientes_service.service.CargaMasivaService;
import com.pacientes_service.pacientes_service.service.PacienteService;
import com.pacientes_service.pacientes_service.client.MedicoClient;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pacientes")
@CrossOrigin(origins = "*")
@Tag(name = "Pacientes", description = "Operaciones relacionadas con la gestión de pacientes y su relación con médicos")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private CargaMasivaService cargaMasivaService;

    @Autowired
    private MedicoClient medicoClient;

    // --- MÉTODOS DE COMUNICACIÓN ---

    @Operation(summary = "Ver médico asignado", description = "Consulta al microservicio de Médicos para obtener datos del profesional asignado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Información consolidada obtenida"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado"),
            @ApiResponse(responseCode = "400", description = "Paciente sin médico asignado")
    })
    @GetMapping("/{id}/ver-medico")
    public ResponseEntity<?> verMedico(@PathVariable Integer id) {
        try {
            Paciente paciente = pacienteService.findById(id);
            if (paciente == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente no encontrado.");

            if (paciente.getMedicoId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El paciente no tiene un médico asignado.");
            }

            MedicoDTO medico = medicoClient.obtenerDatosDelMedico(paciente.getMedicoId());
            return ResponseEntity.ok("PACIENTE: " + paciente.getNombre() + " | MÉDICO: Dr. " + medico.getNombre());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de comunicación: " + e.getMessage());
        }
    }

    // --- CRUD ---

    @Operation(summary = "Listar pacientes", description = "Retorna una lista completa de pacientes registrados.")
    @ApiResponse(responseCode = "200", description = "Lista recuperada con éxito")
    @GetMapping
    public ResponseEntity<List<Paciente>> listar() {
        List<Paciente> pacientes = pacienteService.findAll();
        return pacientes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(pacientes);
    }

    @Operation(summary = "Guardar paciente", description = "Crea un nuevo registro de paciente en la base de datos.")
    @ApiResponse(responseCode = "201", description = "Paciente creado exitosamente",
            content = @Content(schema = @Schema(implementation = Paciente.class)))
    @PostMapping
    public ResponseEntity<Paciente> guardar(@RequestBody Paciente paciente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteService.save(paciente));
    }

    @Operation(summary = "Buscar por ID", description = "Obtiene los detalles de un paciente por su clave primaria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
            @ApiResponse(responseCode = "404", description = "ID no existe en la base de datos")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscar(@PathVariable Integer id) {
        Paciente paciente = pacienteService.findById(id);
        return paciente != null ? ResponseEntity.ok(paciente) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Actualizar paciente", description = "Actualiza la información de un paciente existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente actualizado"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado para actualizar")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizar(@PathVariable Integer id, @RequestBody Paciente paciente) {
        Paciente existente = pacienteService.findById(id);
        if (existente == null) return ResponseEntity.notFound().build();

        paciente.setId(id); // Aseguramos que se actualice el ID correcto
        return ResponseEntity.ok(pacienteService.save(paciente));
    }

    @Operation(summary = "Eliminar paciente", description = "Borra físicamente el registro del paciente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se pudo eliminar: ID no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            pacienteService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Carga masiva", description = "Procesa una lista de DTOs para insertar múltiples registros.")
    @ApiResponse(responseCode = "201", description = "Proceso masivo completado")
    @PostMapping("/masivo")
    public ResponseEntity<String> cargaMasiva(@RequestBody List<PacienteDTO> pacientesDto) {
        cargaMasivaService.procesarCarga(pacientesDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Carga masiva procesada.");
    }
}