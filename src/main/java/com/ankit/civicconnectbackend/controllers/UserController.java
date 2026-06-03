package com.ankit.civicconnectbackend.controllers;

import com.ankit.civicconnectbackend.dto.LoginRequest;
import com.ankit.civicconnectbackend.entities.User;
import com.ankit.civicconnectbackend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

private final UserService userService;

public UserController(UserService userService) {
    this.userService = userService;
}

@GetMapping
public String GetAllUsers() {
    return "users";
}

@PostMapping("/register")
public String addUser(
        @Valid
        @RequestBody User user) {
    //we just pass the json from body then it is converted to object by jackson
    userService.addUser(user);
    return "user added";
}

@PostMapping("/login")
public ResponseEntity<?> login(
        @RequestBody LoginRequest loginRequest
) {


    return ResponseEntity.ok(
            Map.of("token", userService.login(loginRequest)
            )
    );
}


}
