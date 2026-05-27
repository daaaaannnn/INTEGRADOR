# Matriz de roles y módulos

| Rol | Módulos principales | Acciones |
|---|---|---|
| Director | Dashboard, usuarios, prácticas, cursos, instituciones, docentes, reportes, chat | Supervisar, aprobar, crear, editar, activar, inactivar, eliminar, consultar indicadores |
| Coordinador | Grupos, asignación docente, estudiantes, instituciones, control de horas, convenios, chat | Coordinar, vincular estudiantes, asignar docentes, aprobar planes, verificar horas |
| Docente | Revisión de actividades, rúbricas, preguntas, evaluaciones, retroalimentación, chat | Revisar, aprobar/rechazar, retroalimentar, calificar, crear preguntas y rúbricas |
| Estudiante | Paso a paso, matrícula, actividades, documentos, evaluaciones, notas, inquietudes, chat | Cargar hoja de vida, documentos, actividades, responder evaluaciones, consultar notas |
| Institución | Convenio, practicantes, ingreso, horas, evaluación final, certificado, chat | Confirmar ingreso, asignar supervisor, aprobar horas, evaluar desempeño, cerrar práctica |

## Acciones CRUD estándar

Cada módulo que administra datos incluye:

- Crear registro.
- Consultar detalle.
- Actualizar información.
- Activar/inactivar.
- Eliminar cuando el rol tenga permiso.
- Ver historial de revisión cuando aplica.

## Orden visual de estados

1. ACTIVO
2. PENDIENTE
3. APROBADO
4. INACTIVO
5. REPROBADO
