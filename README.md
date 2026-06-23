Sistema de Gestión de Cafetería - Arquitectura de Microservicios

Descripción

El presente proyecto corresponde al desarrollo de un sistema de gestión para una cafetería utilizando arquitectura de microservicios.

La solución permite administrar usuarios, empleados, productos, inventario, ventas y reportes mediante servicios independientes que se comunican a través de APIs REST utilizando OpenFeign.

El objetivo principal es aplicar conceptos de arquitectura distribuida, comunicación entre servicios, seguridad mediante JWT, persistencia en Oracle Database, contenerización con Docker y orquestación mediante Docker Compose.

---

Integrantes

- Matías Posada
- Diego Cortés
- Ignacio Cortés

---

Arquitectura Utilizada

El sistema fue desarrollado siguiendo una arquitectura basada en microservicios utilizando el patrón:

Controller → Service → Repository

Cada microservicio incluye:

- Controladores REST
- Servicios con lógica de negocio
- Repositorios JPA
- DTOs
- OpenFeign
- Swagger/OpenAPI
- JWT
- Manejo de excepciones
- Docker

---

Microservicios Implementados

       Servicio      |   Puerto   |              Función
       
Autenticación Service|    8081    | Gestión de usuarios y generación de JWT

Empleado Service     |    8082    | Administración de empleados

Producto Service     |    8083    | Gestión de productos y categorías

Inventario Service   |    8084    | Gestión de stock y movimientos

Venta Service        |    8085    | Registro de ventas

Reporte Service      |    8086    | Generación de reportes

---

Comunicación Entre Servicios

La comunicación entre microservicios se implementó mediante OpenFeign.

Dependencias del sistema:

- Empleado Service → Autenticación Service
- Inventario Service → Producto Service
- Venta Service → Empleado Service
- Venta Service → Producto Service
- Venta Service → Inventario Service
- Reporte Service → Venta Service
- Reporte Service → Empleado Service
- Reporte Service → Producto Service

---

Seguridad

La seguridad fue implementada mediante:

- Spring Security
- JWT (JSON Web Token)

Características implementadas:

- Autenticación mediante token.
- Protección de endpoints.
- Validación de usuarios autenticados.
- Autorización mediante encabezado Bearer Token.

Ejemplo:

Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

---

Persistencia

Base de datos utilizada:

Oracle Database Cloud

Tecnologías utilizadas:

- Spring Data JPA
- Hibernate
- Oracle JDBC Driver
- Oracle Wallet

Todos los microservicios utilizan una wallet compartida para conectarse a Oracle Cloud.

---

Docker y Docker Compose

El sistema se encuentra completamente dockerizado.

Cada microservicio posee:

- Dockerfile propio.
- Imagen Docker independiente.

La integración completa se realiza mediante Docker Compose.

Levantar el sistema

docker compose up --build

Detener el sistema

docker compose down

---

Estructura del Proyecto


<img width="198" height="255" alt="image" src="https://github.com/user-attachments/assets/d3be94ec-1a54-4f40-a955-81447019228b" />



---

Documentación API

Todos los endpoints se encuentran documentados mediante Swagger/OpenAPI.

Accesos:

- http://localhost:8081/swagger-ui.html
- http://localhost:8082/swagger-ui.html
- http://localhost:8083/swagger-ui.html
- http://localhost:8084/swagger-ui.html
- http://localhost:8085/swagger-ui.html
- http://localhost:8086/swagger-ui.html

---

Tecnologías Utilizadas

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- OpenFeign
- Swagger OpenAPI
- JWT
- Oracle Database
- Docker
- Docker Compose
- Maven
- Lombok
- JUnit 5
- Mockito
- Git
- GitHub

---

Características Implementadas

- Arquitectura basada en microservicios.
- Comunicación entre servicios mediante OpenFeign.
- Seguridad mediante JWT.
- Persistencia con Oracle Database Cloud.
- Dockerización completa.
- Integración mediante Docker Compose.
- Documentación mediante Swagger/OpenAPI.
- Gestión de inventario automática durante ventas.
- Generación de reportes consolidados.

---

Conclusión

El proyecto permitió aplicar conceptos avanzados de arquitectura de microservicios mediante una solución distribuida compuesta por seis servicios independientes, integrados mediante OpenFeign, protegidos con JWT y desplegados utilizando Docker Compose.

La implementación permitió adquirir experiencia práctica en desarrollo backend, integración de servicios, seguridad, persistencia en Oracle Database y despliegue de aplicaciones contenerizadas.
