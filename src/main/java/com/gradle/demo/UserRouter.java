package com.gradle.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class UserRouter {
    @Bean
    public RouterFunction<ServerResponse> route() {
        UserRepository repository = new UserRepositoryImpl();
        UserHandler userHandler = new UserHandler(repository);
        return RouterFunctions
                .route(GET("/user/{id}").and(accept(APPLICATION_JSON)), userHandler::getUser)
                .andRoute(GET("/user").and(accept(APPLICATION_JSON)), userHandler::listUser)
                .andRoute(POST("/user/create").and(contentType(APPLICATION_JSON)), userHandler::createUser);

    }
}
