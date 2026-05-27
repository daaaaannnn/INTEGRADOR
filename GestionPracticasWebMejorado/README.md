# Software para la Gestión de Prácticas Académicas - Versión Web Mejorada

Esta entrega contiene una propuesta mejorada del sistema para ejecutarse desde navegador, con interfaz responsive, módulos por rol, CRUD organizado por estados y una capa backend Java preparada para conectarse con Oracle mediante JDBC.

## Usuarios conservados

| Rol | Correo | Contraseña inicial |
|---|---|---|
| DIRECTOR | admin@proyectojd.com | 123456 |
| ESTUDIANTE | juan@proyectojd.com | 123456 |
| DOCENTE | maria@proyectojd.com | 123456 |
| COORDINADOR | carlos@proyectojd.com | 123456 |
| INSTITUCION | empresa@proyectojd.com | 123456 |

## Estructura del ZIP

```text
GestionPracticasWebMejorado/
├── frontend/                 # Interfaz web responsive HTML, CSS y JavaScript
│   ├── index.html             # Login
│   ├── app.html               # Dashboard multirol
│   └── assets/
├── backend-java/              # API Java ligera con JDBC Oracle
├── database/                  # Scripts Oracle para ajustar la BD y sembrar datos
└── docs/                      # Documentación de cambios, roles, pruebas y bibliografía
```

## Cómo ejecutar la interfaz web

1. Abra `frontend/index.html` para revisar la interfaz visual.
2. Para conectarla con la base de datos, ejecute primero el backend Java.
3. La interfaz consume los endpoints `/api/...` definidos en `frontend/assets/js/api.js`.

## Cómo ejecutar el backend Java

Requisitos recomendados:

- JDK 17 o superior.
- Maven.
- Oracle Database XE o servidor Oracle disponible.
- Usuario de base de datos del proyecto, por ejemplo `proyectojd`.

Pasos:

```bash
cd backend-java
mvn clean package
java -jar target/gestion-practicas-api.jar
```

Por defecto el backend lee `src/main/resources/application.properties`:

```properties
db.url=jdbc:oracle:thin:@localhost:1521:orcl
db.user=proyectojd
db.password=proyectojd
server.port=8080
```

También puede sobrescribirlos con variables de entorno:

```bash
set DB_URL=jdbc:oracle:thin:@localhost:1521:orcl
set DB_USER=proyectojd
set DB_PASSWORD=proyectojd
```

## Scripts de base de datos

Ejecute en SQL Developer o SQL*Plus:

1. `database/01_ajustes_bd_oracle.sql`
2. `database/02_seed_usuarios_y_datos.sql`
3. `database/03_consultas_pruebas_crud.sql`

El archivo `docs/CAMBIOS_BD_REQUERIDOS.md` explica qué campos/tablas nuevas se agregan y por qué.

## Mejoras principales incluidas

- Login sin texto “Oracle 10g”; ahora se muestra “Panel administrativo”.
- Dashboards por rol: Director, Coordinador, Docente, Estudiante e Institución Receptora.
- CRUD para usuarios, cursos, grupos, asignación docente, instituciones, prácticas, actividades, mensajes y notificaciones.
- Orden visual por estados: ACTIVO, PENDIENTE, APROBADO, INACTIVO, REPROBADO.
- Perfil personal con foto, comentario, cambio de contraseña y logo para instituciones.
- Chat interno tipo correo entre usuarios.
- Estudiante con paso a paso, matrícula, actividades pendientes, no entregadas, realizadas, notas y promedio.
- Institución receptora con convenio, practicantes por estado, historial, compatibilidad y cierre/certificación.
- Docente con revisión de actividades, rúbricas, preguntas, evaluaciones y retroalimentación rápida.
- Código organizado, documentado y preparado para ser conectado a Oracle mediante DAOs.

## Nota importante

Esta versión deja lista la arquitectura web y los scripts de ajuste. Si su base de datos actual no tiene alguna tabla o columna indicada en `docs/CAMBIOS_BD_REQUERIDOS.md`, debe agregarla antes de ejecutar el backend completo.
