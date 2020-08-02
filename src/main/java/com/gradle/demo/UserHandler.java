package com.gradle.demo;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public class UserHandler {
    private final UserRepository userRepository;

    public UserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<ServerResponse> listUser(ServerRequest request) {
        Flux<User> user = userRepository.getAllUsers();
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(user, User.class);
    }

    public Mono<ServerResponse> getUser(ServerRequest request) {
        int userId = Integer.parseInt(request.pathVariable("id"));
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        Mono<User> userMono = userRepository.getUserById(userId);
        return userMono.flatMap(user -> ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(BodyInserters.fromValue(user)))
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> createUser(ServerRequest request) {
        System.out.println("in create user");
        Mono<User> user = request.bodyToMono(User.class);
        return ServerResponse.ok().build(userRepository.saveUser(user));
    }
}
