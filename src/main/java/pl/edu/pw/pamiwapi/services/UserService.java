package pl.edu.pw.pamiwapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.pw.pamiwapi.model.ServiceResponse;
import pl.edu.pw.pamiwapi.model.domain.UserEntity;
import pl.edu.pw.pamiwapi.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
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
}
