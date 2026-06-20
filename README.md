Sistema de Gestión de Cafetería - Arquitectura de Microservicios
Descripción

El presente proyecto corresponde al desarrollo de un sistema de gestión para una cafetería utilizando arquitectura de microservicios.

La solución permite administrar productos, categorías, inventario y movimientos de inventario mediante servicios independientes que se comunican a través de APIs REST.

El objetivo principal es aplicar los conceptos de arquitectura de microservicios, separación de responsabilidades, comunicación entre servicios, seguridad, documentación y pruebas unitarias.

Integrantes
Matías Posada
Diego Cortes
Ignacio Cortes

Arquitectura Utilizada

El sistema fue desarrollado utilizando una arquitectura basada en microservicios siguiendo el patrón:

Controller → Service → Repository

Cada microservicio posee:

Controladores REST
Servicios con lógica de negocio
Repositorios JPA
DTOs para intercambio de información
Mappers para conversión de entidades
Manejo centralizado de excepciones
Documentación Swagger
Seguridad JWT
Microservicios Implementados
Producto Service

Responsable de la gestión de productos y categorías.

Funciones principales:

Crear productos
Listar productos
Buscar productos por ID
Actualizar productos
Eliminar productos
Crear categorías
Listar categorías
Buscar categorías por ID
Actualizar categorías
Eliminar categorías
Buscar productos por categoría

Puerto utilizado:

8083

Inventario Service

Responsable de la administración del stock disponible.

Funciones principales:

Crear inventario
Listar inventarios
Buscar inventario por ID
Actualizar inventario
Eliminar inventario
Buscar inventario por producto
Descontar stock
Registrar movimientos automáticamente

Puerto utilizado:

8084

Movimiento de Inventario

Componente perteneciente al Inventario Service.

Funciones principales:

Registrar entradas de stock
Registrar salidas de stock
Consultar movimientos
Buscar movimientos por inventario
Mantener historial de cambios de stock
Comunicación Entre Servicios

La comunicación entre microservicios se implementó mediante OpenFeign.

Inventario Service se comunica con Producto Service para validar la existencia de productos antes de crear registros de inventario.

Esta integración permite mantener la consistencia de los datos y evitar registros inválidos.

Seguridad

La seguridad fue implementada utilizando:

Spring Security
JWT (JSON Web Token)

Características implementadas:

Autenticación mediante token
Protección de endpoints
Validación de usuarios autenticados
Documentación API

Todos los endpoints se encuentran documentados utilizando Swagger OpenAPI.

Acceso local:

Producto Service:

http://localhost:8083/swagger-ui.html

Inventario Service:

http://localhost:8084/swagger-ui.html

Persistencia

Base de datos utilizada:

Oracle Database

Tecnologías utilizadas:

Spring Data JPA
Hibernate
Oracle JDBC Driver
HATEOAS

Se implementó HATEOAS para enriquecer las respuestas REST mediante enlaces relacionados.

Ejemplos:

Producto:

self
categoria

Categoría:

self
productos

Inventario:

self
movimientos

Movimiento:

self
inventario
Validaciones de Negocio Implementadas
Producto
No permite precios menores o iguales a cero.
No permite nombres vacíos.
No permite productos duplicados.
Verifica existencia de categoría.
Categoría
No permite categorías duplicadas.
Inventario
Verifica existencia del producto.
No permite más de un inventario por producto.
Actualiza disponibilidad automáticamente según stock.
Registra movimientos automáticamente.
Movimiento de Inventario
Registra entradas de stock.
Registra salidas de stock.
Mantiene trazabilidad de cambios realizados.
Manejo de Excepciones

Se implementaron excepciones personalizadas para controlar errores de negocio y recursos inexistentes.

Excepciones utilizadas:

ResourceNotFoundException
BusinessException
Pruebas Unitarias

Se desarrollaron pruebas unitarias utilizando:

JUnit 5
Mockito

Servicios evaluados:

Producto Service

Casos de prueba:

Obtener producto por ID.
Crear producto correctamente.
Lanzar excepción cuando la categoría no existe.
Eliminar producto correctamente.
Inventario Service

Casos de prueba:

Obtener inventario por ID.
Crear inventario correctamente.
Lanzar excepción cuando ya existe inventario para el producto.
Descontar stock correctamente.

Total de pruebas implementadas:

8 pruebas unitarias.

Todas las pruebas fueron ejecutadas exitosamente.

Tecnologías Utilizadas
Java 21
Spring Boot
Spring Security
Spring Data JPA
OpenFeign
Swagger OpenAPI
HATEOAS
JWT
Oracle Database
Maven
Lombok
JUnit 5
Mockito
Git
GitHub
Ejecución del Proyecto
Clonar repositorio
git clone https://github.com/Diegoo6/proyectoCafeteria.git
Configurar Base de Datos

Configurar las credenciales de Oracle Database en los archivos de configuración correspondientes.

Ejecutar Microservicios

Desde la raíz de cada microservicio:

mvn spring-boot:run

o ejecutar la clase principal Application.

Acceder a Swagger

Producto Service:

http://localhost:8083/swagger-ui.html

Inventario Service:

http://localhost:8084/swagger-ui.html

Conclusión

El proyecto permitió aplicar conceptos de arquitectura de microservicios, comunicación REST, seguridad con JWT, 
persistencia con Oracle Database, documentación OpenAPI, pruebas unitarias y buenas prácticas de desarrollo utilizando el ecosistema Spring Boot.
