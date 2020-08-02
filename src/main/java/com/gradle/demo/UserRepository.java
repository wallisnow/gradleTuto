package com.gradle.demo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> getUserById(int id);
    Flux<User> getAllUsers();
    Mono<Void> saveUser(Mono<User> user);
}
