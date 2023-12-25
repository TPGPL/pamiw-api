package pl.edu.pw.pamiwapi.services;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pw.pamiwapi.model.ServiceResponse;
import pl.edu.pw.pamiwapi.model.domain.UserEntity;
import pl.edu.pw.pamiwapi.model.dtos.UserLoginDto;
import pl.edu.pw.pamiwapi.model.dtos.UserRegisterDto;
import pl.edu.pw.pamiwapi.repositories.UserRepository;

import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;

    @Autowired
    public UserService(UserRepository repository, JwtService jwtService, PasswordEncoder passwordEncoder, Validator validator) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
    }

    public ServiceResponse<UserEntity> getUserByUsername(String username) {
        var user = repository.findByUsername(username);

        return ServiceResponse.<UserEntity>builder().wasSuccessful(true).data(user.orElse(null)).build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = getUserByUsername(username).getData();

        if (user == null) {
            throw new UsernameNotFoundException("User with the name does not exist.");
        }

        return User.builder().username(user.getUsername()).password(user.getPassword()).build();
    }

    public ServiceResponse<UserEntity> createUser(UserRegisterDto dto) {
        if (repository.existsByEmail(dto.getEmail()) || repository.existsByUsername(dto.getUsername())) {
            return ServiceResponse.<UserEntity>builder().wasSuccessful(false).message("User with this data already exists.").build();
        }

        var violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            return ServiceResponse.<UserEntity>builder().wasSuccessful(false).message(prepareViolationMessage(violations)).build();
        }

        var user = UserEntity.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        return ServiceResponse.<UserEntity>builder().wasSuccessful(true).data(repository.save(user)).build();
    }

    public ServiceResponse<String> authenticate(UserLoginDto dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            return ServiceResponse.<String>builder().wasSuccessful(false).message("Password do not match.").build();
        }

        var violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            return ServiceResponse.<String>builder().wasSuccessful(false).message(prepareViolationMessage(violations)).build();
        }

        var user = getUserByUsername(dto.getUsername()).getData();

        if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            return ServiceResponse.<String>builder().wasSuccessful(false).message("Failed to login.").build();
        }

        var token = jwtService.generateJwt(user.getUsername());

        return ServiceResponse.<String>builder().wasSuccessful(true).message("Authenticated.").data(token).build();
    }

    private <T> String prepareViolationMessage(Set<ConstraintViolation<T>> violations) {
        var err = new StringBuilder();

        for (var v : violations)
            err.append(v.getMessage()).append(" ");

        return err.toString().strip();
    }
}
