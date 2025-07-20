package com.fork.app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("FORK 배달앱 API 문서")
                        .description("AR/AI 기반 배달 서비스")
                        .version("v1.0")
                        .contact(new Contact().name("FORK 팀").email("fork@example.com"))
                );
    }
}
