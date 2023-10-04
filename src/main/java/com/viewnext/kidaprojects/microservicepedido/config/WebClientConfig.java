package com.viewnext.kidaprojects.microservicepedido.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * La clase {@code WebClientConfig} proporciona configuración para crear un WebClient utilizado
 * para realizar solicitudes HTTP a una API remota.
 *
 * <p>
 * El autor de esta clase es Víctor Colorado "Kid A".
 * </p>
 *
 * @version 1.0
 * @since 04 de Octubre de 2023
 */
@Configuration
public class WebClientConfig {

    private static final String BASE_URL = "http://localhost:8080";

    /**
     * Crea y configura un WebClient para interactuar con una API remota en la
     * ubicación especificada por BASE_URL.
     *
     * @return Un objeto WebClient configurado.
     */
    @Bean
    WebClient pedidoWebClient() {
        String apiUrl = BASE_URL;
        return WebClient.create(apiUrl);
    }
}