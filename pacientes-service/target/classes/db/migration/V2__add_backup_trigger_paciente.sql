-- 1. Crear la tabla de respaldo con la misma estructura
CREATE TABLE paciente_backup SELECT * FROM paciente WHERE 1=0;

-- 2. Crear el Trigger para que cada vez que insertes un paciente, se guarde en el backup
DELIMITER //
CREATE TRIGGER despues_insert_paciente
    AFTER INSERT ON paciente
    FOR EACH ROW
BEGIN
    INSERT INTO paciente_backup (id, rut, nombre, apellido, fecha_nacimiento, email, telefono, direccion, medico_id)
    VALUES (NEW.id, NEW.rut, NEW.nombre, NEW.apellido, NEW.fecha_nacimiento, NEW.email, NEW.telefono, NEW.direccion, NEW.medico_id);
END//
DELIMITER ;