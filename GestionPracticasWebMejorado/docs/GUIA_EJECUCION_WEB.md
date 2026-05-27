# Guía de ejecución web

## 1. Preparar base de datos

Ejecute los scripts en este orden:

```sql
@database/01_ajustes_bd_oracle.sql
@database/02_seed_usuarios_y_datos.sql
@database/03_consultas_pruebas_crud.sql
```

Si SQL Developer muestra que una columna ya existe, puede continuar. El script está preparado para ignorar columnas y tablas previamente creadas en la mayoría de casos.

## 2. Configurar backend

Abra `backend-java/src/main/resources/application.properties` y ajuste:

```properties
db.url=jdbc:oracle:thin:@localhost:1521:orcl
db.user=proyectojd
db.password=proyectojd
server.port=8080
```

## 3. Ejecutar backend

```bash
cd backend-java
mvn clean package
java -jar target/gestion-practicas-api.jar
```

## 4. Abrir frontend

Abra en navegador:

```text
frontend/index.html
```

El frontend intentará conectarse a:

```text
http://localhost:8080/api
```

Para cambiar la URL del backend desde consola del navegador:

```javascript
localStorage.setItem('GPA_API_BASE', 'http://localhost:8080/api')
```

## 5. Nota sobre modo visual

La interfaz incluye respaldo local para revisar pantallas cuando el backend no esté ejecutándose. En entrega final, el backend debe estar activo y conectado a Oracle para que los datos sean persistentes.
