package com.restaurante.restaurante.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI restauranteOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Restaurantes")
                        .description("API REST para la gestión de restaurantes de Click and Eat")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Click and Eat")
                                .email("soporte@clickandeat.com")
                        )
                );
    }
}