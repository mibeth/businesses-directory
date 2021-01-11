package com.nl.icwdirectory.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SwaggerConfig {

    @Bean
    OpenAPI icwDirectoryOpenApi() {
        return new OpenAPI()
                .info((new Info().title("International Creative Woman Directory"))
                        .description("Applicaton for businesses's directory management")
                        .version("1.0"));
    }

}
