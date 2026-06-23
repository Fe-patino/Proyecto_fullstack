package com.pedidos.pedidos;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Swagger {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API del Microservicio de Pedidos")
                        .version("1.0.0")
                        .description("Documentación interactiva para la gestión de pedidos y hojas de despacho."));
    }
}