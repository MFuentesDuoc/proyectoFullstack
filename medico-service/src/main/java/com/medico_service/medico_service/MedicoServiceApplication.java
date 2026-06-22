package com.medico_service.medico_service;

import com.medico_service.medico_service.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MedicoServiceApplication {

	@Autowired
	private MedicoService medicoService;

	public static void main(String[] args) {
		SpringApplication.run(MedicoServiceApplication.class, args);
	}

	// Ejecución inicial al arrancar el microservicio
	@Bean
	public CommandLineRunner initData() {
		return args -> {
			// Aquí puedes incluir lógica inicial, como verificar
			// si existen médicos base o configuraciones del sistema.
			System.out.println("Servicio de Médicos iniciado correctamente...");
		};
	}
}