package com.reify.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.reify.login.controller")
                        .or(RequestHandlerSelectors.basePackage("com.reify.supplier.controller")
                                .or(RequestHandlerSelectors.basePackage("com.reify.customer.controller")))
                        .or(RequestHandlerSelectors.basePackage("com.reify.common.controller"))
                .or(RequestHandlerSelectors.basePackage("com.reify.product.controller")))
                .paths(PathSelectors.any())
                .build();
    }

}