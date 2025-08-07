package com.example.teamMatch.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Hackathon Team Matcher API")
                        .version("1.0")
                        .description("This API allows users to form teams for hackathons based on skills, roles, and preferences."));
    }
}
