package com.bookBazaar.controllers;

import com.bookBazaar.models.dto.LoginDTO;
import com.bookBazaar.models.dto.UserDTO;
import com.bookBazaar.services.interfaces.UserServiceINT;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {})
public class UserController {

    private final UserServiceINT userService;

    @PostMapping("/newUser")
    public ResponseEntity<UserDTO> createNewUser(@RequestBody UserDTO userRequest)
    {
        UserDTO response = userService.createNewUser(userRequest);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody LoginDTO loginRequest)
    {
        try
        {
            String response = userService.login(loginRequest);
            return ResponseEntity.ok().body(response);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = {})
    @GetMapping("/specificUser/{username}")
    public ResponseEntity<UserDTO> getSpecificUserByUsername(@PathVariable(value="username")  String username)
    {
        UserDTO response = userService.getUserByUsername(username);
        return ResponseEntity.ok().body(response);
    }


}
