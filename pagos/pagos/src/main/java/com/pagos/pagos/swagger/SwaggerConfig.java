package com.pagos.pagos.swagger;

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
                        .title("Click & Eat — ms-pagos")
                        .description("API REST para la gestión de pagos de la aplicación Click & Eat")
                        .version("1.0.0")
                );
    }

}
