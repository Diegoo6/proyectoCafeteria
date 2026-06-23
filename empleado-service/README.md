# Empleado Service

Microservicio encargado de la gestión de empleados del sistema **Proyecto Cafetería**.

Este servicio permite registrar empleados, listarlos, buscarlos por ID, modificarlos, cambiar su cargo y eliminarlos. Además, se comunica con `autenticacion-service` para validar que el usuario asociado a un empleado exista.

---

## Puerto del servicio

```text
8082
```

URL base local:

```text
http://localhost:8082
```

---

## Tecnologías utilizadas

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- JWT
- Oracle Database
- OpenFeign
- Lombok
- Swagger / OpenAPI
- JUnit
- Mockito
- Maven
- Docker

---

## Responsabilidades principales

- Registrar empleados.
- Listar todos los empleados.
- Buscar empleado por ID.
- Listar empleados por cargo.
- Modificar datos de empleados.
- Cambiar cargo de empleados.
- Eliminar empleados.
- Validar usuarios mediante `autenticacion-service`.
- Proteger endpoints mediante JWT.

---

## Cargos disponibles

El microservicio trabaja con los siguientes cargos:

```text
VENDEDOR
GARZON
COCINERO
```

---

## Endpoints principales

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/v1/empleados` | Registra un nuevo empleado |
| GET | `/api/v1/empleados` | Lista todos los empleados |
| GET | `/api/v1/empleados/{id}` | Busca un empleado por ID |
| GET | `/api/v1/empleados/cargo?tipoCargo=` | Lista empleados por cargo |
| PUT | `/api/v1/empleados/{id}` | Modifica los datos de un empleado |
| PUT | `/api/v1/empleados/{id}/cargo` | Cambia el cargo de un empleado |
| DELETE | `/api/v1/empleados/{id}` | Elimina un empleado |

---

## Seguridad

Este microservicio utiliza **Spring Security** y valida tokens **JWT**.

Para consumir endpoints protegidos, se debe enviar el token generado por `autenticacion-service`.

Formato del token:

```http
Authorization: Bearer TOKEN_GENERADO
```

Ejemplo:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

## Permisos generales

| Acción | Acceso |
|---|---|
| Listar empleados | ADMIN o EMPLEADO |
| Buscar empleado por ID | ADMIN o EMPLEADO |
| Listar empleados por cargo | ADMIN o EMPLEADO |
| Registrar empleado | ADMIN |
| Modificar empleado | ADMIN |
| Cambiar cargo | ADMIN |
| Eliminar empleado | ADMIN |

---

## Comunicación con autenticacion-service

`empleado-service` se comunica con `autenticacion-service` mediante **OpenFeign**.

Esta comunicación se utiliza para validar que el `usuarioId` enviado al registrar un empleado exista realmente.

Flujo general:

```text
Cliente envía solicitud para crear empleado
        ↓
empleado-service recibe los datos
        ↓
empleado-service consulta a autenticacion-service usando Feign
        ↓
autenticacion-service valida si el usuario existe
        ↓
empleado-service registra el empleado
```

Además, el token JWT recibido en la solicitud puede ser reenviado al otro microservicio mediante un interceptor, manteniendo la seguridad en la comunicación interna.

---

## Ejemplo de creación de empleado

```http
POST /api/v1/empleados
```

Body:

```json
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "telefono": "912345678",
  "correo": "juan.perez@correo.com",
  "fechaNacimiento": "1998-05-10",
  "usuarioId": 1,
  "cargo": "VENDEDOR"
}
```

---

## Ejemplo de modificación de empleado

```http
PUT /api/v1/empleados/1
```

Body:

```json
{
  "nombre": "Juan Carlos",
  "apellido": "Pérez",
  "telefono": "987654321",
  "correo": "juan.perez@correo.com",
  "fechaNacimiento": "1998-05-10"
}
```

---

## Ejemplo de cambio de cargo

```http
PUT /api/v1/empleados/1/cargo
```

Body:

```json
{
  "cargo": "COCINERO"
}
```

---

## Base de datos

El servicio utiliza **Oracle Database**.

Ejemplo de configuración en `application.properties`:

```properties
spring.application.name=empleado-service
server.port=8082

spring.datasource.url=jdbc:oracle:thin:@cafeteria_high
spring.datasource.username=EMPLEADO
spring.datasource.password=TU_PASSWORD
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

spring.datasource.hikari.data-source-properties.oracle.net.tns_admin=${TNS_ADMIN:../wallet}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.OracleDialect
spring.jpa.properties.hibernate.format_sql=true
```

---

## Configuración Feign

El microservicio utiliza OpenFeign para comunicarse con `autenticacion-service`.

Ejemplo de propiedad:

```properties
autenticacion-service.url=http://localhost:8081
```

En Docker, esta URL puede cambiar al nombre del contenedor:

```properties
autenticacion-service.url=http://autenticacion-service:8081
```

---

## Swagger

La documentación del microservicio se puede revisar en Swagger.

URL:

```text
http://localhost:8082/swagger-ui/index.html
```

Para probar endpoints protegidos:

1. Iniciar sesión en `autenticacion-service`.
2. Copiar el token generado.
3. Entrar al Swagger de `empleado-service`.
4. Presionar **Authorize**.
5. Pegar el token con el formato:

```text
Bearer TOKEN_GENERADO
```

---

## Ejecución local

Primero se recomienda ejecutar `autenticacion-service`, ya que `empleado-service` depende de él para validar usuarios.

Luego, desde la carpeta de `empleado-service`:

```bash
cd empleado-service
mvn spring-boot:run
```

También se puede ejecutar desde Visual Studio Code o IntelliJ IDEA.

---

## Pruebas unitarias

Para ejecutar las pruebas:

```bash
cd empleado-service
mvn test
```

---

## Docker

Para construir el microservicio con Docker:

```bash
docker build -t empleado-service .
```

Para ejecutarlo:

```bash
docker run -p 8082:8082 empleado-service
```

Si se utiliza Docker Compose desde la raíz del proyecto:

```bash
docker compose up --build empleado-service
```

---

## Estructura general

```text
empleado-service/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/empleado_service/
│   │   │       ├── client/
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── mapper/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       ├── security/
│   │   │       └── service/
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│
├── pom.xml
├── Dockerfile
└── README.md
```

---

## Buenas prácticas aplicadas

- Arquitectura por capas.
- Uso de DTOs para entrada y salida de datos.
- Validación de datos con Jakarta Validation.
- Comunicación entre microservicios mediante OpenFeign.
- Seguridad mediante JWT.
- Separación entre controller, service, repository y model.
- Documentación con Swagger.
- Pruebas unitarias con JUnit y Mockito.
- Preparación para ejecución con Docker.

---

## Autor

**Diego Cortés Salinas**  
DUOC UC - Analista Programador  
Proyecto Cafetería - FullStack I
