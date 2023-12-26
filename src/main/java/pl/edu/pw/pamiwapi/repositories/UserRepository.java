package pl.edu.pw.pamiwapi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pw.pamiwapi.model.domain.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsBy();
    Optional<UserEntity> findByUsername(String username);
}
