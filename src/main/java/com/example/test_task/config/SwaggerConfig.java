package com.example.test_task.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {


    @Bean
    public Docket api() {
        return new Docket(SWAGGER_2)
                .apiInfo(new ApiInfo(
                        "Test Task API"
                        , "Deploy Time : " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm:ss"))
                        , "1.0.0"
                        , ""
                        , new Contact(" : ladimir Plentniov", "https://test", "test@gmail.com")
                        , ""
                        , ""
                        , new HashSet<>()));
    }
}
