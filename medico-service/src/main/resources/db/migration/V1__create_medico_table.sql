CREATE TABLE medico (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        rut VARCHAR(13) UNIQUE NOT NULL,
                        nombre VARCHAR(255) NOT NULL,
                        apellido VARCHAR(255) NOT NULL,
                        especialidad VARCHAR(255) NOT NULL,
                        numero_registro VARCHAR(255) UNIQUE NOT NULL,
                        email VARCHAR(255) NOT NULL,
                        telefono VARCHAR(255)
);