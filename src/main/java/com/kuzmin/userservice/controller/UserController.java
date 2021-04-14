package com.kuzmin.userservice.controller;

import com.kuzmin.userservice.domain.User;
import com.kuzmin.userservice.repository.UserRepository;
import com.kuzmin.userservice.service.ReactiveUserDetailsServiceImpl;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepo;
    private final ReactiveUserDetailsServiceImpl reactiveUserDetailsService;

    public UserController(UserRepository userRepo, ReactiveUserDetailsServiceImpl reactiveUserDetailsService) {
        this.userRepo = userRepo;
        this.reactiveUserDetailsService = reactiveUserDetailsService;
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Cacheable("users")
    public Flux<User> users() {
        return userRepo.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @CachePut("users")
    public Mono<User> users(@RequestBody User user) {
        return reactiveUserDetailsService.save(user);
    }
}
