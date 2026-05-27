# Cambios de base de datos requeridos

Este documento indica los cambios que no estaban completos en el modelo base y que se requieren para que la nueva versión web funcione con todas las UI solicitadas.

## Tablas existentes que requieren ajuste

### USUARIO
Motivo: se necesita activar/inactivar/eliminar usuarios, subir foto, agregar comentario de perfil, cambiar contraseña y soportar los roles del sistema.

Campos requeridos:

- `contrasena_hash VARCHAR2(255)`
- `estado VARCHAR2(20)` con valores recomendados: ACTIVO, INACTIVO, PENDIENTE, APROBADO, REPROBADO
- `foto_perfil_url VARCHAR2(500)`
- `comentario_perfil VARCHAR2(1000)`
- `fecha_ultima_sesion DATE`

### CURSO
Motivo: la UI de cursos debe permitir actualizar, eliminar, activar e inactivar, además de ordenar por estados.

Campos requeridos:

- `estado VARCHAR2(20)`

### PRACTICA
Motivo: el módulo de prácticas debe mostrar fechas, estado, número de práctica y relación más clara con el seguimiento.

Campos requeridos:

- `numero_practica NUMBER`
- `fecha_inicio DATE`
- `fecha_fin DATE`
- `estado VARCHAR2(20)`

### INSTITUCION
Motivo: la institución receptora necesita información completa, estado del servicio, logo, representante, convenio y datos de contacto.

Campos requeridos:

- `nit VARCHAR2(40)`
- `ciudad VARCHAR2(100)`
- `representante VARCHAR2(150)`
- `email VARCHAR2(150)`
- `sitio_web VARCHAR2(250)`
- `logo_url VARCHAR2(500)`
- `estado VARCHAR2(20)`
- `fecha_registro DATE`

### GRUPO
Motivo: el coordinador necesita CRUD completo de grupos de práctica, docente, institución, cupo y estado.

Campos requeridos:

- `id_practica NUMBER`
- `id_institucion NUMBER`
- `id_docente NUMBER`
- `cupo_maximo NUMBER`
- `estado VARCHAR2(20)`

### MATRICULA_PRACTICA
Motivo: el estudiante debe ver matrícula, estado del proceso, paso a paso y fecha límite.

Campos requeridos:

- `fecha_limite DATE`
- `porcentaje_avance NUMBER(5,2)`
- `estado_proceso VARCHAR2(30)`

### REGISTRO_ACTIVIDAD
Motivo: se requiere bitácora con horas, estado, comentarios visibles al reabrir y trazabilidad de revisión.

Campos requeridos:

- `id_matricula_practica NUMBER`
- `horas NUMBER(6,2)`
- `tipo_actividad VARCHAR2(80)`
- `estado VARCHAR2(20)`
- `comentarios VARCHAR2(1000)`

### EVALUACION
Motivo: el docente debe evaluar por rúbrica y el estudiante debe ver nota final, detalle y promedio.

Campos requeridos:

- `id_estudiante NUMBER`
- `id_docente NUMBER`
- `nota_final NUMBER(4,2)`
- `estado VARCHAR2(20)`
- `fecha_evaluacion DATE`

## Tablas nuevas necesarias

### CONVENIO_INSTITUCION
Guarda la información del convenio entre la universidad y la institución receptora.

Campos principales:

- `id_convenio`
- `id_institucion`
- `numero_convenio`
- `fecha_inicio`
- `fecha_fin`
- `objeto`
- `estado`
- `documento_url`

### HISTORIAL_REVISION
Permite saber quién vio, montó, revisó o modificó una actividad, evidencia, evaluación o convenio.

Campos principales:

- `id_historial`
- `entidad`
- `id_entidad`
- `id_usuario`
- `accion`
- `descripcion`
- `fecha_accion`

### MENSAJE_INTERNO
Permite chat/correo interno entre usuarios.

Campos principales:

- `id_mensaje`
- `id_remitente`
- `id_destinatario`
- `asunto`
- `mensaje`
- `leido`
- `fecha_envio`

### NOTIFICACION
Permite avisar pendientes, evaluaciones nuevas, documentos faltantes y vencimientos.

Campos principales:

- `id_notificacion`
- `id_usuario`
- `titulo`
- `mensaje`
- `tipo`
- `estado`
- `fecha_creacion`

### DOCUMENTO_ESTUDIANTE
Guarda hoja de vida, acta de inicio, ARL, informes de avance, informe final y evidencias.

Campos principales:

- `id_documento`
- `id_usuario`
- `id_matricula_practica`
- `tipo_documento`
- `nombre_archivo`
- `ruta_archivo`
- `estado`
- `fecha_carga`

### SUPERVISOR_INSTITUCION
Registra tutores internos o supervisores de la institución receptora.

Campos principales:

- `id_supervisor`
- `id_institucion`
- `nombre`
- `cargo`
- `correo`
- `telefono`
- `estado`

### OFERTA_PRACTICA
Permite que la institución publique vacantes u oportunidades de práctica.

Campos principales:

- `id_oferta`
- `id_institucion`
- `titulo`
- `descripcion`
- `modalidad`
- `cupos`
- `estado`
- `fecha_publicacion`

### COMPATIBILIDAD_INSTITUCION
Permite recomendar instituciones al estudiante según área, intereses, resultados de prueba o perfil.

Campos principales:

- `id_compatibilidad`
- `id_usuario`
- `id_institucion`
- `porcentaje`
- `criterios`
- `fecha_calculo`

### CERTIFICADO_PRACTICA
Guarda la trazabilidad del certificado final.

Campos principales:

- `id_certificado`
- `id_matricula_practica`
- `codigo_verificacion`
- `fecha_emision`
- `ruta_pdf`
- `estado`

### RESPUESTA_RAPIDA_DOCENTE
Permite retroalimentación rápida sin cargar editores pesados.

Campos principales:

- `id_respuesta_rapida`
- `id_docente`
- `titulo`
- `contenido`
- `estado`

## Recomendación para estados

Usar una lista común en todos los módulos:

- ACTIVO
- INACTIVO
- PENDIENTE
- APROBADO
- REPROBADO
- FINALIZADO
- REQUIERE_REVISION

La interfaz ya ordena los estados visualmente en este orden: activos, pendientes, aprobados, inactivos y reprobados.
