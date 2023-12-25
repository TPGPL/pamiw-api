package pl.edu.pw.pamiwapi.controllers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.pamiwapi.model.dtos.UserLoginDto;
import pl.edu.pw.pamiwapi.model.dtos.UserRegisterDto;
import pl.edu.pw.pamiwapi.model.domain.UserEntity;
import pl.edu.pw.pamiwapi.repositories.UserRepository;
import pl.edu.pw.pamiwapi.services.JwtService;

import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    private final AuthenticationManager manager;
    private final JwtService jwtService;
    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder;
    private final Validator validator;

    @Autowired
    public AuthController(AuthenticationManager manager, JwtService jwtService, UserRepository repository, BCryptPasswordEncoder encoder, Validator validator) {
        this.manager = manager;
        this.jwtService = jwtService;
        this.repository = repository;
        this.encoder = encoder;
        this.validator = validator;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDto dto) {
        if (repository.existsByEmail(dto.getEmail()) || repository.existsByUsername(dto.getUsername())) {
            return new ResponseEntity<>("User with this data already exists.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        var violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            return new ResponseEntity<>(prepareViolationMessage(violations), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        UserEntity user = UserEntity.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .build();

        repository.save(user);

        return new ResponseEntity<>("User registered successfully.", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            return new ResponseEntity<>("Passwords do not match.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        var violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            return new ResponseEntity<>(prepareViolationMessage(violations), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        var auth = manager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);

        var token = jwtService.generateJwt(auth);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    private <T> String prepareViolationMessage(Set<ConstraintViolation<T>> violations) {
        var err = new StringBuilder();

        for (var v : violations)
            err.append(v.getMessage()).append(" ");

        return err.toString().strip();
    }
}
