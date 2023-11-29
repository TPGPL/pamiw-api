package pl.edu.pw.pamiwapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.pw.pamiwapi.repositories.UserRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    @Autowired
    public AppUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!repository.existsByUsername(username)) {
            throw new UsernameNotFoundException("User does not exist.");
        }

        var user = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User does not exist."));

        return User.builder().username(user.getUsername()).password(user.getPassword()).build();
    }
}
