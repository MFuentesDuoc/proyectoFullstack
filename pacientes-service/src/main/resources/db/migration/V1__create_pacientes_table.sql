CREATE TABLE paciente (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          rut VARCHAR(13) UNIQUE NOT NULL,
                          nombre VARCHAR(255) NOT NULL,
                          apellido VARCHAR(255) NOT NULL,
                          fecha_nacimiento DATE,
                          email VARCHAR(255) NOT NULL,
                          telefono VARCHAR(255),
                          direccion VARCHAR(255),
                          medico_id INT
);