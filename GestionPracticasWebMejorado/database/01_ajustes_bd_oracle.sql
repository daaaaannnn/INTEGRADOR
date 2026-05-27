SET SERVEROUTPUT ON;

CREATE OR REPLACE PROCEDURE gp_add_column(p_table VARCHAR2, p_column_def VARCHAR2) AS
BEGIN
    EXECUTE IMMEDIATE 'ALTER TABLE ' || p_table || ' ADD (' || p_column_def || ')';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE NOT IN (-1430, -955) THEN
            DBMS_OUTPUT.PUT_LINE('Aviso: ' || p_table || ' -> ' || SQLERRM);
        END IF;
END;
/

BEGIN
    gp_add_column('USUARIO', 'contrasena_hash VARCHAR2(255)');
    gp_add_column('USUARIO', 'estado VARCHAR2(20) DEFAULT ''ACTIVO'' NOT NULL');
    gp_add_column('USUARIO', 'foto_perfil_url VARCHAR2(500)');
    gp_add_column('USUARIO', 'comentario_perfil VARCHAR2(1000)');
    gp_add_column('USUARIO', 'fecha_ultima_sesion DATE');

    gp_add_column('CURSO', 'estado VARCHAR2(20) DEFAULT ''ACTIVO'' NOT NULL');

    gp_add_column('PRACTICA', 'numero_practica NUMBER');
    gp_add_column('PRACTICA', 'fecha_inicio DATE');
    gp_add_column('PRACTICA', 'fecha_fin DATE');
    gp_add_column('PRACTICA', 'estado VARCHAR2(20) DEFAULT ''ACTIVO'' NOT NULL');

    gp_add_column('INSTITUCION', 'nit VARCHAR2(40)');
    gp_add_column('INSTITUCION', 'ciudad VARCHAR2(100)');
    gp_add_column('INSTITUCION', 'representante VARCHAR2(150)');
    gp_add_column('INSTITUCION', 'email VARCHAR2(150)');
    gp_add_column('INSTITUCION', 'sitio_web VARCHAR2(250)');
    gp_add_column('INSTITUCION', 'logo_url VARCHAR2(500)');
    gp_add_column('INSTITUCION', 'estado VARCHAR2(20) DEFAULT ''ACTIVO'' NOT NULL');
    gp_add_column('INSTITUCION', 'fecha_registro DATE DEFAULT SYSDATE');

    gp_add_column('GRUPO', 'id_practica NUMBER');
    gp_add_column('GRUPO', 'id_institucion NUMBER');
    gp_add_column('GRUPO', 'id_docente NUMBER');
    gp_add_column('GRUPO', 'cupo_maximo NUMBER DEFAULT 30');
    gp_add_column('GRUPO', 'estado VARCHAR2(20) DEFAULT ''ACTIVO'' NOT NULL');

    gp_add_column('MATRICULA_PRACTICA', 'fecha_limite DATE');
    gp_add_column('MATRICULA_PRACTICA', 'porcentaje_avance NUMBER(5,2) DEFAULT 0');
    gp_add_column('MATRICULA_PRACTICA', 'estado_proceso VARCHAR2(30) DEFAULT ''PENDIENTE''');

    gp_add_column('REGISTRO_ACTIVIDAD', 'id_matricula_practica NUMBER');
    gp_add_column('REGISTRO_ACTIVIDAD', 'horas NUMBER(6,2) DEFAULT 0');
    gp_add_column('REGISTRO_ACTIVIDAD', 'tipo_actividad VARCHAR2(80)');
    gp_add_column('REGISTRO_ACTIVIDAD', 'estado VARCHAR2(20) DEFAULT ''PENDIENTE'' NOT NULL');
    gp_add_column('REGISTRO_ACTIVIDAD', 'comentarios VARCHAR2(1000)');

    gp_add_column('EVALUACION', 'id_estudiante NUMBER');
    gp_add_column('EVALUACION', 'id_docente NUMBER');
    gp_add_column('EVALUACION', 'nota_final NUMBER(4,2)');
    gp_add_column('EVALUACION', 'estado VARCHAR2(20) DEFAULT ''PENDIENTE'' NOT NULL');
    gp_add_column('EVALUACION', 'fecha_evaluacion DATE DEFAULT SYSDATE');
    gp_add_column('ASIGNACION_DOCENTE', 'estado VARCHAR2(20) DEFAULT ''ACTIVO'' NOT NULL');
END;
/

DROP PROCEDURE gp_add_column;

BEGIN
    EXECUTE IMMEDIATE 'CREATE TABLE CONVENIO_INSTITUCION (
        id_convenio NUMBER PRIMARY KEY,
        id_institucion NUMBER NOT NULL,
        numero_convenio VARCHAR2(80) NOT NULL,
        fecha_inicio DATE NOT NULL,
        fecha_fin DATE,
        objeto VARCHAR2(1000),
        estado VARCHAR2(20) DEFAULT ''ACTIVO'' NOT NULL,
        documento_url VARCHAR2(500),
        CONSTRAINT fk_conv_institucion FOREIGN KEY (id_institucion) REFERENCES INSTITUCION(id_institucion)
    )';
EXCEPTION WHEN OTHERS THEN IF SQLCODE != -955 THEN RAISE; END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'CREATE TABLE HISTORIAL_REVISION (
        id_historial NUMBER PRIMARY KEY,
        entidad VARCHAR2(80) NOT NULL,
        id_entidad NUMBER NOT NULL,
        id_usuario NUMBER NOT NULL,
        accion VARCHAR2(80) NOT NULL,
        descripcion VARCHAR2(1000),
        fecha_accion DATE DEFAULT SYSDATE NOT NULL,
        CONSTRAINT fk_hist_usuario FOREIGN KEY (id_usuario) REFERENCES USUARIO(id_usuario)
    )';
EXCEPTION WHEN OTHERS THEN IF SQLCODE != -955 THEN RAISE; END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'CREATE TABLE MENSAJE_INTERNO (
        id_mensaje NUMBER PRIMARY KEY,
        id_remitente NUMBER NOT NULL,
        id_destinatario NUMBER NOT NULL,
        asunto VARCHAR2(150),
        mensaje VARCHAR2(2000) NOT NULL,
        leido CHAR(1) DEFAULT ''N'' NOT NULL,
        fecha_envio DATE DEFAULT SYSDATE NOT NULL,
        CONSTRAINT fk_msg_rem FOREIGN KEY (id_remitente) REFERENCES USUARIO(id_usuario),
        CONSTRAINT fk_msg_des FOREIGN KEY (id_destinatario) REFERENCES USUARIO(id_usuario)
    )';
EXCEPTION WHEN OTHERS THEN IF SQLCODE != -955 THEN RAISE; END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'CREATE TABLE NOTIFICACION (
        id_notificacion NUMBER PRIMARY KEY,
        id_usuario NUMBER NOT NULL,
        titulo VARCHAR2(150) NOT NULL,
        mensaje VARCHAR2(1000),
        tipo VARCHAR2(40) DEFAULT ''INFO'',
        estado VARCHAR2(20) DEFAULT ''PENDIENTE'' NOT NULL,
        fecha_creacion DATE DEFAULT SYSDATE NOT NULL,
        CONSTRAINT fk_notif_usuario FOREIGN KEY (id_usuario) REFERENCES USUARIO(id_usuario)
    )';
EXCEPTION WHEN OTHERS THEN IF SQLCODE != -955 THEN RAISE; END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'CREATE TABLE DOCUMENTO_ESTUDIANTE (
        id_documento NUMBER PRIMARY KEY,
        id_usuario NUMBER NOT NULL,
        id_matricula_practica NUMBER,
        tipo_documento VARCHAR2(80) NOT NULL,
        nombre_archivo VARCHAR2(250) NOT NULL,
        ruta_archivo VARCHAR2(500),
        estado VARCHAR2(20) DEFAULT ''PENDIENTE'' NOT NULL,
        fecha_carga DATE DEFAULT SYSDATE NOT NULL,
        CONSTRAINT fk_doc_usuario FOREIGN KEY (id_usuario) REFERENCES USUARIO(id_usuario)
    )';
EXCEPTION WHEN OTHERS THEN IF SQLCODE != -955 THEN RAISE; END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'CREATE TABLE SUPERVISOR_INSTITUCION (
        id_supervisor NUMBER PRIMARY KEY,
        id_institucion NUMBER NOT NULL,
        nombre VARCHAR2(150) NOT NULL,
        cargo VARCHAR2(120),
        correo VARCHAR2(150),
        telefono VARCHAR2(40),
        estado VARCHAR2(20) DEFAULT ''ACTIVO'' NOT NULL,
        CONSTRAINT fk_sup_institucion FOREIGN KEY (id_institucion) REFERENCES INSTITUCION(id_institucion)
    )';
EXCEPTION WHEN OTHERS THEN IF SQLCODE != -955 THEN RAISE; END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'CREATE TABLE OFERTA_PRACTICA (
        id_oferta NUMBER PRIMARY KEY,
        id_institucion NUMBER NOT NULL,
        titulo VARCHAR2(180) NOT NULL,
        descripcion VARCHAR2(1500),
        modalidad VARCHAR2(40),
        cupos NUMBER DEFAULT 1,
        estado VARCHAR2(20) DEFAULT ''ACTIVO'' NOT NULL,
        fecha_publicacion DATE DEFAULT SYSDATE NOT NULL,
        CONSTRAINT fk_oferta_institucion FOREIGN KEY (id_institucion) REFERENCES INSTITUCION(id_institucion)
    )';
EXCEPTION WHEN OTHERS THEN IF SQLCODE != -955 THEN RAISE; END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'CREATE TABLE COMPATIBILIDAD_INSTITUCION (
        id_compatibilidad NUMBER PRIMARY KEY,
        id_usuario NUMBER NOT NULL,
        id_institucion NUMBER NOT NULL,
        porcentaje NUMBER(5,2) DEFAULT 0 NOT NULL,
        criterios VARCHAR2(1000),
        fecha_calculo DATE DEFAULT SYSDATE NOT NULL,
        CONSTRAINT fk_comp_usuario FOREIGN KEY (id_usuario) REFERENCES USUARIO(id_usuario),
        CONSTRAINT fk_comp_inst FOREIGN KEY (id_institucion) REFERENCES INSTITUCION(id_institucion)
    )';
EXCEPTION WHEN OTHERS THEN IF SQLCODE != -955 THEN RAISE; END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'CREATE TABLE CERTIFICADO_PRACTICA (
        id_certificado NUMBER PRIMARY KEY,
        id_matricula_practica NUMBER NOT NULL,
        codigo_verificacion VARCHAR2(80) NOT NULL,
        fecha_emision DATE DEFAULT SYSDATE NOT NULL,
        ruta_pdf VARCHAR2(500),
        estado VARCHAR2(20) DEFAULT ''GENERADO'' NOT NULL,
        CONSTRAINT fk_cert_matricula FOREIGN KEY (id_matricula_practica) REFERENCES MATRICULA_PRACTICA(id_matricula_practica)
    )';
EXCEPTION WHEN OTHERS THEN IF SQLCODE != -955 THEN RAISE; END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'CREATE TABLE RESPUESTA_RAPIDA_DOCENTE (
        id_respuesta_rapida NUMBER PRIMARY KEY,
        id_docente NUMBER NOT NULL,
        titulo VARCHAR2(120) NOT NULL,
        contenido VARCHAR2(1000) NOT NULL,
        estado VARCHAR2(20) DEFAULT ''ACTIVO'' NOT NULL,
        CONSTRAINT fk_resp_rap_docente FOREIGN KEY (id_docente) REFERENCES USUARIO(id_usuario)
    )';
EXCEPTION WHEN OTHERS THEN IF SQLCODE != -955 THEN RAISE; END IF;
END;
/

BEGIN EXECUTE IMMEDIATE 'CREATE SEQUENCE seq_convenio START WITH 1 INCREMENT BY 1'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -955 THEN RAISE; END IF; END;
/
BEGIN EXECUTE IMMEDIATE 'CREATE SEQUENCE seq_historial START WITH 1 INCREMENT BY 1'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -955 THEN RAISE; END IF; END;
/
BEGIN EXECUTE IMMEDIATE 'CREATE SEQUENCE seq_mensaje START WITH 1 INCREMENT BY 1'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -955 THEN RAISE; END IF; END;
/
BEGIN EXECUTE IMMEDIATE 'CREATE SEQUENCE seq_notificacion START WITH 1 INCREMENT BY 1'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -955 THEN RAISE; END IF; END;
/
BEGIN EXECUTE IMMEDIATE 'CREATE SEQUENCE seq_documento_est START WITH 1 INCREMENT BY 1'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -955 THEN RAISE; END IF; END;
/
BEGIN EXECUTE IMMEDIATE 'CREATE SEQUENCE seq_supervisor_inst START WITH 1 INCREMENT BY 1'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -955 THEN RAISE; END IF; END;
/
BEGIN EXECUTE IMMEDIATE 'CREATE SEQUENCE seq_oferta_practica START WITH 1 INCREMENT BY 1'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -955 THEN RAISE; END IF; END;
/
BEGIN EXECUTE IMMEDIATE 'CREATE SEQUENCE seq_compatibilidad START WITH 1 INCREMENT BY 1'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -955 THEN RAISE; END IF; END;
/
BEGIN EXECUTE IMMEDIATE 'CREATE SEQUENCE seq_certificado START WITH 1 INCREMENT BY 1'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -955 THEN RAISE; END IF; END;
/
BEGIN EXECUTE IMMEDIATE 'CREATE SEQUENCE seq_resp_rapida START WITH 1 INCREMENT BY 1'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -955 THEN RAISE; END IF; END;
/

CREATE OR REPLACE TRIGGER trg_convenio_bi
BEFORE INSERT ON CONVENIO_INSTITUCION
FOR EACH ROW
BEGIN
    IF :NEW.id_convenio IS NULL THEN
        SELECT seq_convenio.NEXTVAL INTO :NEW.id_convenio FROM dual;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_historial_bi
BEFORE INSERT ON HISTORIAL_REVISION
FOR EACH ROW
BEGIN
    IF :NEW.id_historial IS NULL THEN
        SELECT seq_historial.NEXTVAL INTO :NEW.id_historial FROM dual;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_mensaje_bi
BEFORE INSERT ON MENSAJE_INTERNO
FOR EACH ROW
BEGIN
    IF :NEW.id_mensaje IS NULL THEN
        SELECT seq_mensaje.NEXTVAL INTO :NEW.id_mensaje FROM dual;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_notificacion_bi
BEFORE INSERT ON NOTIFICACION
FOR EACH ROW
BEGIN
    IF :NEW.id_notificacion IS NULL THEN
        SELECT seq_notificacion.NEXTVAL INTO :NEW.id_notificacion FROM dual;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_doc_est_bi
BEFORE INSERT ON DOCUMENTO_ESTUDIANTE
FOR EACH ROW
BEGIN
    IF :NEW.id_documento IS NULL THEN
        SELECT seq_documento_est.NEXTVAL INTO :NEW.id_documento FROM dual;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_supervisor_bi
BEFORE INSERT ON SUPERVISOR_INSTITUCION
FOR EACH ROW
BEGIN
    IF :NEW.id_supervisor IS NULL THEN
        SELECT seq_supervisor_inst.NEXTVAL INTO :NEW.id_supervisor FROM dual;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_oferta_bi
BEFORE INSERT ON OFERTA_PRACTICA
FOR EACH ROW
BEGIN
    IF :NEW.id_oferta IS NULL THEN
        SELECT seq_oferta_practica.NEXTVAL INTO :NEW.id_oferta FROM dual;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_compatibilidad_bi
BEFORE INSERT ON COMPATIBILIDAD_INSTITUCION
FOR EACH ROW
BEGIN
    IF :NEW.id_compatibilidad IS NULL THEN
        SELECT seq_compatibilidad.NEXTVAL INTO :NEW.id_compatibilidad FROM dual;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_certificado_bi
BEFORE INSERT ON CERTIFICADO_PRACTICA
FOR EACH ROW
BEGIN
    IF :NEW.id_certificado IS NULL THEN
        SELECT seq_certificado.NEXTVAL INTO :NEW.id_certificado FROM dual;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_resp_rapida_bi
BEFORE INSERT ON RESPUESTA_RAPIDA_DOCENTE
FOR EACH ROW
BEGIN
    IF :NEW.id_respuesta_rapida IS NULL THEN
        SELECT seq_resp_rapida.NEXTVAL INTO :NEW.id_respuesta_rapida FROM dual;
    END IF;
END;
/
