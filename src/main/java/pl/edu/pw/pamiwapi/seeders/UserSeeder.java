package pl.edu.pw.pamiwapi.seeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edu.pw.pamiwapi.model.domain.UserEntity;
import pl.edu.pw.pamiwapi.model.domain.UserRole;
import pl.edu.pw.pamiwapi.repositories.UserRepository;

@Component
public class UserSeeder {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserSeeder(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void seed() {
        if (repository.existsBy()) {
            return;
        }

        var adminUser = UserEntity.builder()
                .email("admin@test.com")
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .role(UserRole.Admin)
                .build();

        repository.save(adminUser);

        var regularUser = UserEntity.builder()
                .email("user@test.com")
                .username("user")
                .password(passwordEncoder.encode("password"))
                .role(UserRole.User)
                .build();

        repository.save(regularUser);
    }
}
