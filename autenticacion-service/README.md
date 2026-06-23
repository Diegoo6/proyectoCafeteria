# Autenticación Service

Microservicio encargado de la autenticación y gestión de usuarios del sistema **Proyecto Cafetería**.

Este servicio permite registrar usuarios, iniciar sesión, generar tokens JWT, consultar usuarios, cambiar roles y eliminar usuarios. Forma parte de una arquitectura basada en microservicios desarrollada con Spring Boot.

---

## Puerto del servicio

```text
8081
```

URL base local:

```text
http://localhost:8081
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
- Lombok
- Swagger / OpenAPI
- JUnit
- Mockito
- Maven
- Docker

---

## Responsabilidades principales

- Registrar nuevos usuarios.
- Iniciar sesión.
- Generar token JWT.
- Validar credenciales.
- Buscar usuario por ID.
- Listar usuarios por rol.
- Cambiar rol de usuario.
- Eliminar usuarios.
- Proteger endpoints mediante Spring Security.

---

## Roles del sistema

El microservicio trabaja con los siguientes roles:

```text
ADMIN
EMPLEADO
```

El usuario registrado normalmente recibe el rol `EMPLEADO`.

El rol `ADMIN` tiene permisos para realizar acciones administrativas, como cambiar roles o eliminar usuarios.

---

## Endpoints principales

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/v1/autenticacion/registrar` | Registra un nuevo usuario |
| POST | `/api/v1/autenticacion/login` | Inicia sesión y genera un token JWT |
| GET | `/api/v1/autenticacion/usuarios/{id}` | Busca un usuario por ID |
| GET | `/api/v1/autenticacion/usuarios/rol?rol=` | Lista usuarios por rol |
| PUT | `/api/v1/autenticacion/usuarios/{id}/cambiar-rol` | Cambia el rol de un usuario |
| DELETE | `/api/v1/autenticacion/usuarios/{id}` | Elimina un usuario |

---

## Seguridad

Este microservicio utiliza **Spring Security** con autenticación basada en **JWT**.

Flujo general:

1. El usuario se registra o inicia sesión.
2. El sistema valida las credenciales.
3. Se genera un token JWT.
4. El token se utiliza para acceder a endpoints protegidos.

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
| Registrar usuario | Público |
| Login | Público |
| Buscar usuario por ID | Usuario autenticado |
| Listar usuarios por rol | Usuario autenticado |
| Cambiar rol | ADMIN |
| Eliminar usuario | ADMIN |

---

## Ejemplo de registro

```http
POST /api/v1/autenticacion/registrar
```

Body:

```json
{
  "username": "usuario1",
  "password": "1234"
}
```

---

## Ejemplo de login

```http
POST /api/v1/autenticacion/login
```

Body:

```json
{
  "username": "admin",
  "password": "admin"
}
```

Respuesta esperada:

```json
{
  "token": "TOKEN_GENERADO"
}
```

---

## Ejemplo de cambio de rol

```http
PUT /api/v1/autenticacion/usuarios/1/cambiar-rol
```

Body:

```json
{
  "nuevoRol": "ADMIN"
}
```

---

## Base de datos

El servicio utiliza **Oracle Database**.

Ejemplo de configuración en `application.properties`:

```properties
spring.application.name=autenticacion-service
server.port=8081

spring.datasource.url=jdbc:oracle:thin:@cafeteria_high
spring.datasource.username=AUTH
spring.datasource.password=TU_PASSWORD
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

spring.datasource.hikari.data-source-properties.oracle.net.tns_admin=${TNS_ADMIN:../wallet}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.OracleDialect
spring.jpa.properties.hibernate.format_sql=true
```

---

## Swagger

La documentación del microservicio se puede revisar en Swagger.

URL:

```text
http://localhost:8081/swagger-ui/index.html
```

Para probar endpoints protegidos:

1. Iniciar sesión en `/login`.
2. Copiar el token generado.
3. Presionar el botón **Authorize** en Swagger.
4. Pegar el token con el formato:

```text
Bearer TOKEN_GENERADO
```

---

## Ejecución local

Desde la carpeta del microservicio:

```bash
cd autenticacion-service
mvn spring-boot:run
```

También se puede ejecutar desde Visual Studio Code o IntelliJ IDEA.

---

## Pruebas unitarias

Para ejecutar las pruebas:

```bash
cd autenticacion-service
mvn test
```

---

## Docker

Para construir el microservicio con Docker:

```bash
docker build -t autenticacion-service .
```

Para ejecutarlo:

```bash
docker run -p 8081:8081 autenticacion-service
```

Si se utiliza Docker Compose desde la raíz del proyecto:

```bash
docker compose up --build autenticacion-service
```

---

## Estructura general

```text
autenticacion-service/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/autenticacion_service/
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
- Encriptación de contraseñas con BCrypt.
- Seguridad mediante JWT.
- Validaciones con Jakarta Validation.
- Documentación con Swagger.
- Pruebas unitarias con JUnit y Mockito.
- Preparación para ejecución con Docker.

---

## Autor

**Diego Cortés Salinas**  
DUOC UC - Analista Programador  
Proyecto Cafetería - FullStack I
