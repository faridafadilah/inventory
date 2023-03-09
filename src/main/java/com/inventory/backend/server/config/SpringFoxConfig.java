package com.inventory.backend.server.config;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {
  @Bean
    public Docket api() {                
        return new Docket(DocumentationType.SWAGGER_2)          
          .select()
          .apis(RequestHandlerSelectors.basePackage("com.inventory.backend.server.controller"))
          .paths(PathSelectors.any())                          
          .build()
          .apiInfo(apiInfo())
          .useDefaultResponseMessages(false)
          .directModelSubstitute(LocalDate.class, java.sql.Date.class)
          .genericModelSubstitutes(ResponseEntity.class)
          .protocols(new HashSet<>(Arrays.asList("HTTP", "HTTPS")))
          .enableUrlTemplating(true);
    }
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
          .components(new Components())
          .info(new Info().title("My Application").version("1.0.0"))
          .servers(Arrays.asList(
              new Server().url("http://localhost:8080"),
              new Server().url("https://localhost:8443")
          ));
    }
 
    private ApiInfo apiInfo() {
        return new ApiInfo(
          "My REST API",
          "Some custom description of API.",
          "1.0",
          "Terms of service",
          new Contact("John Doe", "www.example.com", "myeaddress@company.com"),
          "License of API", "API license URL", Collections.emptyList());
    }
}
