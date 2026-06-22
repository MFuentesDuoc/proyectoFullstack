package com.pacientes_service.pacientes_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicoDTO {
    // Usamos los mismos nombres de variables para que Jackson los mapee automáticamente
    private Integer id;
    private String nombre;
    private String apellido;
    private String especialidad;
    private String email;
    private String telefono;

    // Puedes omitir el RUT o el número de registro si el servicio de
    // pacientes no los necesita para mostrar la información básica.
}