package resenas.resenas.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI reseniasOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Resenias")
                        .description("API REST para la gestion de resenias de pedidos, restaurantes " +
                                "y repartidores en la plataforma Click and Eat")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Click and Eat")
                                .email("soporte@clickandeat.com")
                        )
                );
    }
}