package com.mythik.orderManager.config;



import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfoBuilder() {
        return new OpenAPI()
                .info(new Info()
                        .title("Mythik Order Manager")
                        .version("1.0.0")
                        .description("Order Manager APIs")
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("license").url("http://springdoc.org")));

    }
}
