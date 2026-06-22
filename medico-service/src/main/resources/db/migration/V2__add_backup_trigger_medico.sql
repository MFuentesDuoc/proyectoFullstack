-- 1. Crear la tabla de respaldo
CREATE TABLE medico_backup SELECT * FROM medico WHERE 1=0;

-- 2. Crear el Trigger
DELIMITER //
CREATE TRIGGER despues_insert_medico
    AFTER INSERT ON medico
    FOR EACH ROW
BEGIN
    INSERT INTO medico_backup (id, rut, nombre, apellido, especialidad, numero_registro, email, telefono)
    VALUES (NEW.id, NEW.rut, NEW.nombre, NEW.apellido, NEW.especialidad, NEW.numero_registro, NEW.email, NEW.telefono);
END//
DELIMITER ;