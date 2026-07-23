package com.dhaneesh.seatsure.user.controller;

import com.dhaneesh.seatsure.user.dto.CreateUserRequest;
import com.dhaneesh.seatsure.user.dto.UserResponse;
import com.dhaneesh.seatsure.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //@valid annotation is used her to check the validity of the request
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));

    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> searchuser(@PathVariable long id)
    {
        return ResponseEntity.ok(userService.getUserById(id));
    }

}