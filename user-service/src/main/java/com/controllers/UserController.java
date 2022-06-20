package com.controllers;

import com.entities.User;
import com.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.ws.rs.DELETE;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public String saveUser(@RequestBody User user) {
        return userService.saveAndValidate(user);
    }

    @GetMapping(value = "/{email}")
    public User getUser(
            @RequestHeader(value = "email") String email) {
        return userService.getDataForEmail(email);
    }

    @PostMapping(value = "/delete")
    public void validateUserLogin(@RequestBody String email) {
        userService.removeKeyData(email);
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.PUT)
    public String updateUser(@RequestBody User user) {
        return userService.validateAndUpdate(user);
    }

}
