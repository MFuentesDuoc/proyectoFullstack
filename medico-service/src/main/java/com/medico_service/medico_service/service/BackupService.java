package com.medico_service.medico_service.service;
import org.springframework.stereotype.Service;
@Service
public class BackupService {
    public void createBackup() {
        String dumpPath = "C:/laragon/bin/mysql/mysql-8.0.30-winx64/bin/mysqldump";
        String dbName = "db_hospital_medicos";
        String dbUser = "root";
        String dbPass = "";
        String savePath = "C:/backups/backup_medico_service.sql";

        String command = String.format("%s -u %s %s --databases %s -r %s",
                dumpPath, dbUser, dbPass.isEmpty() ? "" : "-p" + dbPass, dbName, savePath);

        try {
            Process process = Runtime.getRuntime().exec(command);
            int processComplete = process.waitFor();
            if (processComplete == 0) {
                System.out.println("Backup de médicos creado con éxito");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}