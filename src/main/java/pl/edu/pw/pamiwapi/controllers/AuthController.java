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
    public ResponseEntity<ServiceResponse<String>> register(@RequestBody UserRegisterDto dto) {
        var response = userService.createUser(dto);

        return new ResponseEntity<>(ServiceResponse.<String>builder().
                success(response.isSuccess())
                .message(response.isSuccess() ? "User registered successfully." : response.getMessage()).build(),
                response.isSuccess() ? HttpStatus.OK : HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @PostMapping("/login")
    public ResponseEntity<ServiceResponse<JwtResponse>> login(@RequestBody UserLoginDto dto) {
        var response = userService.authenticate(dto);

        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.FORBIDDEN);
    }
}
