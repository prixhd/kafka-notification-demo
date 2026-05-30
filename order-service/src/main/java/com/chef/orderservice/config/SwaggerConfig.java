package com.chef.orderservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Order Service API")
                        .description("Сервис создания заказов с отправкой событий в Kafka")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Chef")
                                .email("chef@example.com")));
    }
}