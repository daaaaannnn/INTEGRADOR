SET SERVEROUTPUT ON;

CREATE OR REPLACE PROCEDURE gp_upsert_usuario(
    p_nombre VARCHAR2,
    p_apellido VARCHAR2,
    p_correo VARCHAR2,
    p_rol VARCHAR2,
    p_password VARCHAR2,
    p_telefono VARCHAR2
) AS
    v_count NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_count FROM USUARIO WHERE correo_electronico = p_correo;
    IF v_count = 0 THEN
        INSERT INTO USUARIO(id_usuario, nombre, apellido, correo_electronico, telefono, rol, contrasena_hash, estado, comentario_perfil)
        SELECT NVL(MAX(id_usuario),0)+1, p_nombre, p_apellido, p_correo, p_telefono, p_rol, p_password, 'ACTIVO', 'Usuario base del proyecto académico'
        FROM USUARIO;
    ELSE
        UPDATE USUARIO
        SET nombre = p_nombre,
            apellido = p_apellido,
            telefono = p_telefono,
            rol = p_rol,
            contrasena_hash = NVL(contrasena_hash, p_password),
            estado = 'ACTIVO'
        WHERE correo_electronico = p_correo;
    END IF;
END;
/

BEGIN
    gp_upsert_usuario('Director','Programa','admin@proyectojd.com','DIRECTOR','123456','3000000001');
    gp_upsert_usuario('Juan','Estudiante','juan@proyectojd.com','ESTUDIANTE','123456','3000000002');
    gp_upsert_usuario('Maria','Docente','maria@proyectojd.com','DOCENTE','123456','3000000003');
    gp_upsert_usuario('Coordinador','Practica','carlos@proyectojd.com','COORDINADOR','123456','3000000004');
    gp_upsert_usuario('Institucion','Receptora','empresa@proyectojd.com','INSTITUCION','123456','3000000005');
END;
/

DROP PROCEDURE gp_upsert_usuario;

BEGIN
    INSERT INTO PROGRAMA(id_programa, nombre, proyecto, resolucion_min)
    SELECT NVL(MAX(id_programa),0)+1, 'Licenciatura en Educación', 'Gestión de Prácticas Académicas', 'MEN-2026'
    FROM PROGRAMA
    WHERE NOT EXISTS (SELECT 1 FROM PROGRAMA WHERE nombre = 'Licenciatura en Educación');
EXCEPTION WHEN OTHERS THEN DBMS_OUTPUT.PUT_LINE(SQLERRM);
END;
/

BEGIN
    INSERT INTO INSTITUCION(id_institucion, nombre, direccion, telefono, contacto_nombre, fecha_inicio_convenio, fecha_fin_convenio, nit, ciudad, representante, email, sitio_web, estado, fecha_registro)
    SELECT NVL(MAX(id_institucion),0)+1, 'Colegio Técnico Integrado', 'Carrera 10 # 20-30', '6070000000', 'Rector Académico', SYSDATE, ADD_MONTHS(SYSDATE, 24), '900123456-1', 'Bucaramanga', 'Rector Académico', 'contacto@colegiotecnico.edu.co', 'https://colegiotecnico.edu.co', 'APROBADO', SYSDATE
    FROM INSTITUCION
    WHERE NOT EXISTS (SELECT 1 FROM INSTITUCION WHERE nombre = 'Colegio Técnico Integrado');
EXCEPTION WHEN OTHERS THEN DBMS_OUTPUT.PUT_LINE(SQLERRM);
END;
/

BEGIN
    INSERT INTO CONVENIO_INSTITUCION(id_institucion, numero_convenio, fecha_inicio, fecha_fin, objeto, estado, documento_url)
    SELECT i.id_institucion, 'CONV-UDI-2026-001', SYSDATE, ADD_MONTHS(SYSDATE, 24), 'Convenio para desarrollo y seguimiento de prácticas académicas', 'ACTIVO', 'docs/convenio-udi-2026-001.pdf'
    FROM INSTITUCION i
    WHERE i.nombre = 'Colegio Técnico Integrado'
      AND NOT EXISTS (SELECT 1 FROM CONVENIO_INSTITUCION c WHERE c.numero_convenio = 'CONV-UDI-2026-001');
EXCEPTION WHEN OTHERS THEN DBMS_OUTPUT.PUT_LINE(SQLERRM);
END;
/

BEGIN
    INSERT INTO NOTIFICACION(id_usuario, titulo, mensaje, tipo, estado)
    SELECT u.id_usuario, 'Completa tu paso a paso', 'Carga tu hoja de vida, documentos de inicio y registra tu primera actividad cuando tengas matrícula aprobada.', 'PENDIENTE', 'PENDIENTE'
    FROM USUARIO u
    WHERE u.correo_electronico = 'juan@proyectojd.com'
      AND NOT EXISTS (SELECT 1 FROM NOTIFICACION n WHERE n.id_usuario = u.id_usuario AND n.titulo = 'Completa tu paso a paso');
EXCEPTION WHEN OTHERS THEN DBMS_OUTPUT.PUT_LINE(SQLERRM);
END;
/

COMMIT;
