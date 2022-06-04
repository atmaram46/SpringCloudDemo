package com.controllers;

import com.entities.LoginDetails;
import com.entities.User;
import com.entities.value_objects.ResponseTemplateVO;
import com.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public User saveUser(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping(value = "/{id}/{role}")
    public ResponseTemplateVO getUser(
            @RequestHeader(value = "id") Long userId,
            @RequestHeader(value = "role") String role) {
        return userService.getUserWithDepartment(userId);
    }

    @PostMapping(value = "/login")
    public User validateUserLogin(@RequestBody LoginDetails loginDetails) {
        return userService.getByLoginDetails(loginDetails.getEmail(), loginDetails.getPassword());
    }

    @GetMapping(value = "/secure")
    public String getSecure() {
        return "Secure endpoint available";
    }
}
