# Pruebas funcionales recomendadas

## Login y roles

1. Ingresar con `admin@proyectojd.com / 123456` y verificar dashboard Director.
2. Ingresar con `carlos@proyectojd.com / 123456` y verificar dashboard Coordinador.
3. Ingresar con `maria@proyectojd.com / 123456` y verificar dashboard Docente.
4. Ingresar con `juan@proyectojd.com / 123456` y verificar dashboard Estudiante.
5. Ingresar con `empresa@proyectojd.com / 123456` y verificar dashboard Institución.

## CRUD

- Crear, editar, activar/inactivar y eliminar un curso.
- Crear, editar, activar/inactivar y eliminar un grupo.
- Crear una institución, asignarle convenio y cambiar su estado.
- Registrar una actividad del estudiante y verificar que el docente pueda verla.
- Revisar una actividad y confirmar que se guarde el historial.
- Enviar un mensaje interno y verificar recepción.
- Crear notificación de pendiente y marcarla como leída.

## Base de datos

- Ejecutar consultas de `database/03_consultas_pruebas_crud.sql`.
- Validar claves foráneas de convenio, seguimiento, documentos y compatibilidad.
- Verificar que los estados se ordenen correctamente.
