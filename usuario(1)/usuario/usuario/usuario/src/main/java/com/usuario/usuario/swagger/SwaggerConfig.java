package com.usuario.usuario.swagger;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Click & Eat — ms-usuarios")
                        .description("API REST para la gestión de usuarios de la aplicación Click & Eat")
                        .version("1.0.0")
                );
    }

}
