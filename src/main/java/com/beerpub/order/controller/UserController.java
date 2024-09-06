package com.beerpub.order.controller;

import com.beerpub.order.Response;
import com.beerpub.order.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public Response signup(@RequestParam String username, @RequestParam String password,
                         @RequestParam String phoneNumber, @RequestParam String role){
        if (userService.signup(username, password, phoneNumber, role)){
            return Response.newSuccess("user " + username + " sign-up successfully");
        }else{
            return Response.newFail("user " + username + " already exists");
        }
    }

    @PostMapping("/login")
    public Response login(@RequestParam String username, @RequestParam String password){
        if (userService.login(username, password)){
            return Response.newSuccess("user " + username + " login successfully");
        }else{
            return Response.newFail("login failed");
        }
    }
}
