package com.example.ecommerceapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // Bu sınıfın bir Spring konfigürasyon sınıfı olduğunu belirtir.
public class OpenApiConfig {

    @Bean // Bu metodun döndürdüğü nesnenin Spring konteynerine bir bean olarak ekleneceğini belirtir.
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("E-Commerce Backend API") // API başlığı
                        .version("1.0") // API versiyonu
                        .description("E-Commerce uygulamasının backend servisleri için REST API dokümantasyonu.")); // API açıklaması
    }
}