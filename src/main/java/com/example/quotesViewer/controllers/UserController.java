package com.example.quotesViewer.controllers;

import com.example.quotesViewer.api.response.QuotesViewerResponse;
import com.example.quotesViewer.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/user/")
    public ResponseEntity<QuotesViewerResponse> createUser(@RequestParam(value = "name", defaultValue = "") String name,
                                                           @RequestParam(value = "email", defaultValue = "") String email,
                                                           @RequestParam(value = "password", defaultValue = "") String password) {
        return userService.createUser(name, email, password);
    }

}
