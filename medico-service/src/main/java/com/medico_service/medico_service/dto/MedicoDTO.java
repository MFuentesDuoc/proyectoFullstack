package com.medico_service.medico_service.dto;

import lombok.Data;

// ==========================================
// IMPORTACIÓN DE SWAGGER (SPRINGDOC)
// ==========================================
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Objeto de Transferencia de Datos (DTO) para la gestión de Médicos, usado principalmente para recepcionar datos en peticiones como la carga masiva.")
public class MedicoDTO {

    @Schema(description = "Identificador único del médico (generalmente no es necesario enviarlo al crear uno nuevo)", example = "1")
    private Integer id;

    @Schema(description = "RUT del médico con formato válido", example = "12.345.678-9", required = true)
    private String rut;

    @Schema(description = "Nombre del médico", example = "Juan", required = true)
    private String nombre;

    @Schema(description = "Apellido del médico", example = "Pérez", required = true)
    private String apellido;

    @Schema(description = "Especialidad principal del médico", example = "Cardiología", required = true)
    private String especialidad;

    @Schema(description = "Número de registro en la superintendencia o colegio médico", example = "25489-7", required = true)
    private String numeroRegistro;

    @Schema(description = "Correo electrónico institucional o personal", example = "juan.perez@hospital.cl", required = true)
    private String email;

    @Schema(description = "Número de teléfono de contacto (opcional)", example = "+56912345678")
    private String telefono;
}