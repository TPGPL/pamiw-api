package pl.edu.pw.pamiwapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.pamiwapi.model.ServiceResponse;
import pl.edu.pw.pamiwapi.model.dtos.JwtResponse;
import pl.edu.pw.pamiwapi.model.dtos.UserLoginDto;
import pl.edu.pw.pamiwapi.model.dtos.UserRegisterDto;
import pl.edu.pw.pamiwapi.services.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {

        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDto dto) {
        var response = userService.createUser(dto);

        if (!response.isSuccess()) {
            var message = response.getMessage() == null ? "Failed to register." : response.getMessage();

            return new ResponseEntity<>(message, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return new ResponseEntity<>("User registered successfully.", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ServiceResponse<JwtResponse>> login(@RequestBody UserLoginDto dto) {
        var response = userService.authenticate(dto);

        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.FORBIDDEN);
    }
}
