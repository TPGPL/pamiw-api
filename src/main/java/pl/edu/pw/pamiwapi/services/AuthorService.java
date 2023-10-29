package pl.edu.pw.pamiwapi.services;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pw.pamiwapi.models.Author;
import pl.edu.pw.pamiwapi.repositories.AuthorRepository;
import pl.edu.pw.pamiwapi.utils.ServiceResponse;

import java.util.Set;

@Service
public class AuthorService {
    private final AuthorRepository repository;
    private final Validator validator;

    @Autowired
    public AuthorService(AuthorRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public Iterable<Author> getAll() {
        return repository.findAll();
    }

    public Author getById(int id) {
        return repository.findById(id).orElse(null);
    }

    public ServiceResponse<Author> create(Author author) {
        var violations = validator.validate(author);

        if (!violations.isEmpty()) {
            var message = new StringBuilder();

            for (var v : violations) {
                message.append(v.getMessage()).append(" ");
            }

            return ServiceResponse.<Author>builder()
                    .message(message.toString())
                    .wasSuccessful(false).build();
        }

        return ServiceResponse.<Author>builder()
                .data(repository.save(author))
                .wasSuccessful(true)
                .build();
    }

    // TODO: UD
}
