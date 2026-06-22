package com.pacientes_service.pacientes_service.service;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class BackupScheduler {
    private final BackupService backupService;

    public BackupScheduler(BackupService backupService) {
        this.backupService = backupService;
    }

    // Se ejecuta automáticamente a las 12:00 AM todos los días [cite: 86-87]
    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleBackup() {
        backupService.createBackup(); // [cite: 89]
    }
}