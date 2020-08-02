package com.gradle.demo;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class UserWebClient {
    private WebClient client = WebClient.create("http://localhost:8080");
    // For getting all users
    private Mono<ClientResponse> result = client.get()
            .uri("/user")
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

    // Getting user by ID
    private Mono<User> singleUser = client.get()
            .uri("/user/1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .flatMap(res -> res.bodyToMono(User.class));


    public List<User> getResult() {
        Flux<User> userList = result.flatMapMany(res -> res.bodyToFlux(User.class));
        return userList.collectList().block();
    }
}
