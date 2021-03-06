package com.controllers;

import com.entities.AuthRequest;
import com.entities.AuthResponse;
import com.entities.LoginRequest;
import com.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> validateLogin(@RequestBody LoginRequest loginReq) {
        AuthResponse response = authService.findDetails(loginReq);
        if(response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
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
