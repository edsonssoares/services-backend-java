package com.soulcode.services.Controllers;


import com.soulcode.services.Models.User;
import com.soulcode.services.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("services")

public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/usuarios")
    public List<User> usuarios() {

        return userService.listar();
    }

    @PostMapping("/usuarios")
    public ResponseEntity<User> inserirUsuario(@RequestBody User user) {
        String senhaCodificada = passwordEncoder.encode(user.getPassword());
        user.setPassword(senhaCodificada);
        user = userService.cadastrar(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }



}
