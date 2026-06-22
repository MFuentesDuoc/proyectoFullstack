package com.medico_service.medico_service.controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.flywaydb.core.Flyway;

@RestController
@RequestMapping("/admin/db")
public class DatabaseAdminController {
    private final Flyway flyway;

    public DatabaseAdminController(Flyway flyway) {
        this.flyway = flyway;
    }

    @PostMapping("/repair")
    public String repairDatabase() {
        flyway.repair();
        return "Historial de Flyway (Médicos) reparado.";
    }
}