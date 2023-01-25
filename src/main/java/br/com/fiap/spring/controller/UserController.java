package br.com.fiap.spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import br.com.fiap.spring.model.dto.auth.*;
import br.com.fiap.spring.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody CreateUserDTO createUserDTO) {
        return userService.create(createUserDTO);
    }

    @PostMapping(value = "login", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtDTO login(@RequestBody AuthDTO authDTO) {
        return userService.login(authDTO);
    }
}