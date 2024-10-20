package com.gateway.service.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {

        return routeLocatorBuilder.routes()
                .route(r -> r.path("/quiz/**")
                        .filters(f -> f.rewritePath("/quiz/?(?<segment>.*)", "/${segment}")
                                .addResponseHeader("-X-CUSTOM-HEADER", "Added for Quiz by Pavan"))

                        .uri("lb://QUIZ-SERVICE")
                )
                .route(r -> r.path("/question/**")
                        .filters(f -> f.rewritePath("/question/?(?<segment>.*)", "/${segment}")
                                .addResponseHeader("-X-CUSTOM-HEADER", "Added for Question"))
                        .uri("lb://QUESTION-SERVICE"))
                .build();
    }
}
