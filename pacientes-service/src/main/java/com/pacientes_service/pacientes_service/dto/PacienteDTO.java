package com.pacientes_service.pacientes_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Date;

@Data
@Schema(description = "Objeto que representa los datos de un paciente para transferencia y carga masiva")
public class PacienteDTO {

    @Schema(description = "Identificador único del paciente", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "RUT del paciente con guion y dígito verificador", example = "12345678-9")
    private String rut;

    @Schema(description = "Nombre(s) del paciente", example = "Juan Pablo")
    private String nombre;

    @Schema(description = "Apellido(s) del paciente", example = "Pérez")
    private String apellido;

    @Schema(description = "Fecha de nacimiento en formato AAAA-MM-DD", example = "1995-05-15")
    private Date fechaNacimiento;

    @Schema(description = "Correo electrónico de contacto", example = "juan.perez@example.com")
    private String email;

    @Schema(description = "Número telefónico de contacto", example = "+56912345678")
    private String telefono;

    @Schema(description = "Dirección de domicilio", example = "Av. Concha y Toro 1340, Puente Alto")
    private String direccion;

    @Schema(description = "ID del médico asignado (ID entre 1 y 5)", example = "1")
    private Integer medicoId; // Campo corregido y mapeado
}