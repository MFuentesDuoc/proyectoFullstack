package com.pacientes_service.pacientes_service;

import com.pacientes_service.pacientes_service.service.UsuarioService;
import org.springframework.cloud.openfeign.EnableFeignClients; // <--- AGREGA ESTA LÍNEA
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients // Ahora el IDE debería reconocerlo
public class PacientesServiceApplication {

	@Autowired
	private UsuarioService usuarioService;

	public static void main(String[] args) {
		SpringApplication.run(PacientesServiceApplication.class, args);
	}

	// Esto se ejecuta automáticamente cuando arranca la aplicación
	@Bean
	public CommandLineRunner crearUsuarioAdmin() {
		return args -> {
			usuarioService.crearAdminSiNoExiste();
		};
	}
}