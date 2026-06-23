package com.gateway.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
        System.out.println("===============================================");
        System.out.println(" API Gateway iniciado correctamente");
        System.out.println(" URL: http://localhost:8090");
        System.out.println("-----------------------------------------------");
        System.out.println(" /api/v1/usuarios/**     -> USUARIO");
        System.out.println(" /api/v1/repartidores/** -> REPARTIDORES");
        System.out.println(" /api/v1/pagos/**        -> PAGOS");
        System.out.println("-----------------------------------------------");
        System.out.println(" Eureka: http://localhost:8761");
        System.out.println("===============================================");
    }
}