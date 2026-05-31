SET SERVEROUTPUT ON;

PROMPT ============================================================
PROMPT PARCHE GPA - correos, fechas y columnas base para version web
PROMPT Ejecutar conectado como PROYECTOJD
PROMPT ============================================================

CREATE OR REPLACE PROCEDURE gp_add_column_safe(p_table VARCHAR2, p_column_def VARCHAR2) AS
BEGIN
    EXECUTE IMMEDIATE 'ALTER TABLE ' || p_table || ' ADD (' || p_column_def || ')';
    DBMS_OUTPUT.PUT_LINE('OK columna agregada en ' || p_table || ': ' || p_column_def);
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE = -1430 THEN
            DBMS_OUTPUT.PUT_LINE('OK ya existe columna en ' || p_table || ': ' || p_column_def);
        ELSE
            DBMS_OUTPUT.PUT_LINE('Aviso ' || p_table || ' -> ' || SQLERRM);
        END IF;
END;
/

BEGIN
    gp_add_column_safe('USUARIO', 'correo_electronico VARCHAR2(150)');
    gp_add_column_safe('USUARIO', 'contrasena_hash VARCHAR2(255)');
    gp_add_column_safe('USUARIO', 'rol VARCHAR2(30) DEFAULT ''ESTUDIANTE'' NOT NULL');
    gp_add_column_safe('USUARIO', 'estado VARCHAR2(20) DEFAULT ''ACTIVO'' NOT NULL');
    gp_add_column_safe('USUARIO', 'foto_perfil_url VARCHAR2(500)');
    gp_add_column_safe('USUARIO', 'comentario_perfil VARCHAR2(1000)');
    gp_add_column_safe('USUARIO', 'fecha_ultima_sesion DATE');

    gp_add_column_safe('NOTIFICACION', 'fecha_creacion DATE DEFAULT SYSDATE NOT NULL');
    gp_add_column_safe('NOTIFICACION', 'tipo VARCHAR2(40) DEFAULT ''INFO''');
    gp_add_column_safe('NOTIFICACION', 'estado VARCHAR2(20) DEFAULT ''PENDIENTE'' NOT NULL');

    gp_add_column_safe('MENSAJE_INTERNO', 'fecha_envio DATE DEFAULT SYSDATE NOT NULL');
    gp_add_column_safe('MENSAJE_INTERNO', 'leido CHAR(1) DEFAULT ''N'' NOT NULL');
END;
/

PROMPT Copiando correos desde columnas antiguas si existen...
DECLARE
    v_count NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_count
    FROM USER_TAB_COLUMNS
    WHERE TABLE_NAME = 'USUARIO' AND COLUMN_NAME = 'EMAIL';

    IF v_count > 0 THEN
        EXECUTE IMMEDIATE 'UPDATE USUARIO SET correo_electronico = email WHERE correo_electronico IS NULL AND email IS NOT NULL';
        DBMS_OUTPUT.PUT_LINE('Correos copiados desde EMAIL');
    END IF;

    SELECT COUNT(*) INTO v_count
    FROM USER_TAB_COLUMNS
    WHERE TABLE_NAME = 'USUARIO' AND COLUMN_NAME = 'CORREO';

    IF v_count > 0 THEN
        EXECUTE IMMEDIATE 'UPDATE USUARIO SET correo_electronico = correo WHERE correo_electronico IS NULL AND correo IS NOT NULL';
        DBMS_OUTPUT.PUT_LINE('Correos copiados desde CORREO');
    END IF;
END;
/

PROMPT Insertando o actualizando usuarios base...
CREATE OR REPLACE PROCEDURE gp_upsert_usuario_web(
    p_nombre VARCHAR2,
    p_apellido VARCHAR2,
    p_correo VARCHAR2,
    p_rol VARCHAR2,
    p_password VARCHAR2,
    p_telefono VARCHAR2
) AS
    v_count NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_count FROM USUARIO WHERE LOWER(correo_electronico) = LOWER(p_correo);

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
        WHERE LOWER(correo_electronico) = LOWER(p_correo);
    END IF;
END;
/

BEGIN
    gp_upsert_usuario_web('Director','Programa','admin@proyectojd.com','DIRECTOR','123456','3000000001');
    gp_upsert_usuario_web('Juan','Estudiante','juan@proyectojd.com','ESTUDIANTE','123456','3000000002');
    gp_upsert_usuario_web('Maria','Docente','maria@proyectojd.com','DOCENTE','123456','3000000003');
    gp_upsert_usuario_web('Coordinador','Practica','carlos@proyectojd.com','COORDINADOR','123456','3000000004');
    gp_upsert_usuario_web('Institucion','Receptora','empresa@proyectojd.com','INSTITUCION','123456','3000000005');
END;
/

DROP PROCEDURE gp_upsert_usuario_web;
DROP PROCEDURE gp_add_column_safe;

COMMIT;

PROMPT Verificacion de columnas principales
SELECT column_name
FROM user_tab_columns
WHERE table_name = 'USUARIO'
  AND column_name IN ('ID_USUARIO','NOMBRE','APELLIDO','CORREO_ELECTRONICO','ROL','ESTADO','CONTRASENA_HASH')
ORDER BY column_name;

SELECT id_usuario, nombre, apellido, correo_electronico, rol, estado
FROM usuario
ORDER BY id_usuario;
