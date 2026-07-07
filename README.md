# Click and Eat  — Arquitectura de Microservicios

## Descripción
Plataforma de pedidos de comida a domicilio desarrollada con arquitectura de microservicios usando Spring Boot, Spring Cloud y Docker.

## Integrantes del equipo
- Felix Patino
- Dylan Riquelme
- Vicente Bravo
- Benjamin Hernandez

## Microservicios implementados

| Microservicio | Puerto | Descripción |
|---|---|---|
| ms-usuario | 8080 | Gestión de usuarios de la plataforma |
| ms-repartidores | 8081 | Gestión de repartidores |
| ms-pagos | 8082 | Procesamiento de pagos |
| ms-pedidos | 8083 | Gestión de pedidos |
| ms-productos | 8084 | Catálogo de productos |
| ms-carrito | 8085 | Carrito de compras |
| ms-restaurante | 8086 | Gestión de restaurantes |
| ms-resenias | 8087 | Reseñas de pedidos y restaurantes |
| ms-notificaciones | 8088 | Envío de notificaciones |
| ms-categorias | 8089 | Categorías de productos |
| eureka-server | 8761 | Servidor de registro de servicios |
| api-gateway | 8090 | Punto de entrada unificado |

## Rutas del API Gateway

| Ruta | Microservicio |
|---|---|
| /api/v1/usuarios/** | ms-usuario |
| /api/restaurantes/** | ms-restaurante |
| /api/productos/** | ms-productos |
| /api/pedidos/** | ms-pedidos |
| /api/carrito/** | ms-carrito |
| /api/v1/pagos/** | ms-pagos |
| /api/v1/repartidores/** | ms-repartidores |
| /api/resenias/** | ms-resenias |
| /api/notificaciones/** | ms-notificaciones |
| /api/categorias/** | ms-categorias |

## URLs de Swagger

| Microservicio | URL |
|---|---|
| ms-usuario | http://localhost:8080/swagger-ui/index.html |
| ms-restaurante | http://localhost:8086/swagger-ui/index.html |
| ms-productos | http://localhost:8084/swagger-ui/index.html |
| ms-pedidos | http://localhost:8083/swagger-ui/index.html |
| ms-carrito | http://localhost:8085/swagger-ui/index.html |
| ms-pagos | http://localhost:8082/swagger-ui/index.html |
| ms-repartidores | http://localhost:8081/swagger-ui/index.html |
| ms-resenias | http://localhost:8087/swagger-ui/index.html |
| ms-notificaciones | http://localhost:8088/swagger-ui/index.html |
| ms-categorias | http://localhost:8089/swagger-ui/index.html |
| Eureka Dashboard | http://localhost:8761 |

## Ejecución local

### Con XAMPP/Laragon
1. Iniciar MySQL
2. Levantar Eureka: `cd eureka-server && mvn spring-boot:run`
3. Levantar cada microservicio: `mvn spring-boot:run`
4. Levantar API Gateway al final

### Con Docker
1. Tener Docker Desktop abierto
2. Generar JARs: `mvn clean package -DskipTests` en cada microservicio
3. Levantar todo: `docker-compose up --build`
4. Acceder a Eureka: http://localhost:8761

## Tecnologías utilizadas
- Java 21
- Spring Boot 3.4.1
- Spring Cloud 2024.0.0
- Spring Data JPA + Hibernate
- MySQL 8.0
- Docker + Docker Compose
- Eureka Server (Service Discovery)
- Spring Cloud Gateway
- Swagger / OpenAPI 3 (springdoc 2.7.0)
- JUnit 5 + Mockito