package com.medico_service.medico_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

// ==========================================
// IMPORTACIÓN DE SWAGGER (SPRINGDOC)
// ==========================================
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "medico")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa a un médico en el sistema")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único autogenerado del médico", example = "1")
    private Integer id;

    @Column(unique = true, length = 13, nullable = false)
    @Schema(description = "RUT del médico con formato válido", example = "12.345.678-9", required = true)
    private String rut;

    @Column(nullable = false)
    @Schema(description = "Nombre del médico", example = "Juan", required = true)
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Apellido del médico", example = "Pérez", required = true)
    private String apellido;

    @Column(nullable = false)
    @Schema(description = "Especialidad principal del médico", example = "Cardiología", required = true)
    private String especialidad;

    @Column(name = "numero_registro", unique = true, nullable = false)
    @Schema(description = "Número de registro en la superintendencia o colegio médico", example = "25489-7", required = true)
    private String numeroRegistro;

    @Column(nullable = false)
    @Schema(description = "Correo electrónico institucional o personal", example = "juan.perez@hospital.cl", required = true)
    private String email;

    @Column(nullable = true)
    @Schema(description = "Número de teléfono de contacto (opcional)", example = "+56912345678")
    private String telefono;

}