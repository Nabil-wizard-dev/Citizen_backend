package Nabil.Simplice.app.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Gestion de communaute locale")
                        .version("1.0")
                        .contact(new Contact().email("Nabil.wizarddev@gmail.com & simpliceobryan@gmail.com"))
                        .contact(new Contact().name("-- Nabil & Simplice --"))
                        .description("Documentation de l'API SPRING BOOT -- Gestion De Communaute Locale --"));
    }
}
