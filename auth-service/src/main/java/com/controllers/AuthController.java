package com.controllers;

import com.entities.AuthRequest;
import com.entities.AuthResponse;
import com.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.register(authRequest));
    }

    @PostMapping(value = "/updateUser")
    public ResponseEntity<AuthResponse> validateLogin(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.update(authRequest));
    }

    @GetMapping(value = "/test")
    public ResponseEntity<String> testingEndPoint() {
        return ResponseEntity.ok("Well Done!!!");
    }

    @GetMapping(value = "/test2")
    public ResponseEntity<String> testDataFromRegister() {
        return ResponseEntity.ok(authService.getDataFromRegister());
    }

}
