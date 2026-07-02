package com.carrito.carrito.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebConfig {

    @Bean
    @LoadBalanced // Permite usar los nombres de los microservicios registrados en Eureka en vez de localhost:puerto
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}