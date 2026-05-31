SET SERVEROUTPUT ON;

SELECT id_usuario, nombre, apellido, correo_electronico, rol, estado
FROM USUARIO
ORDER BY DECODE(estado,'ACTIVO',1,'PENDIENTE',2,'APROBADO',3,'INACTIVO',4,'REPROBADO',5,6), nombre;

SELECT id_institucion, nombre, nit, ciudad, representante, estado
FROM INSTITUCION
ORDER BY DECODE(estado,'ACTIVO',1,'PENDIENTE',2,'APROBADO',3,'INACTIVO',4,'REPROBADO',5,6), nombre;

SELECT c.numero_convenio, i.nombre AS institucion, c.fecha_inicio, c.fecha_fin, c.estado
FROM CONVENIO_INSTITUCION c
JOIN INSTITUCION i ON i.id_institucion = c.id_institucion;

SELECT n.id_notificacion, u.correo_electronico, n.titulo, n.estado, n.fecha_creacion
FROM NOTIFICACION n
JOIN USUARIO u ON u.id_usuario = n.id_usuario
ORDER BY n.fecha_creacion DESC;

INSERT INTO MENSAJE_INTERNO(id_remitente, id_destinatario, asunto, mensaje)
SELECT d.id_usuario, e.id_usuario, 'Bienvenida al sistema', 'Recuerda revisar tus pendientes y cargar tus documentos iniciales.'
FROM USUARIO d, USUARIO e
WHERE d.correo_electronico = 'admin@proyectojd.com'
  AND e.correo_electronico = 'juan@proyectojd.com';

SELECT m.asunto, m.mensaje, r.correo_electronico AS remitente, d.correo_electronico AS destinatario
FROM MENSAJE_INTERNO m
JOIN USUARIO r ON r.id_usuario = m.id_remitente
JOIN USUARIO d ON d.id_usuario = m.id_destinatario
ORDER BY m.fecha_envio DESC;

ROLLBACK;
