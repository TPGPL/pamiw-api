package pl.edu.pw.pamiwapi.services;

import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pw.pamiwapi.dtos.AuthorDto;
import pl.edu.pw.pamiwapi.models.Author;
import pl.edu.pw.pamiwapi.repositories.AuthorRepository;
import pl.edu.pw.pamiwapi.utils.ServiceResponse;

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
            return ServiceResponse.createInvalidResponse(violations);
        }

        if (repository.existsByEmail(author.getEmail())) {
            return ServiceResponse.<Author>builder()
                    .message("The author with given email already exists.")
                    .build();
        }

        return ServiceResponse.<Author>builder()
                .data(repository.save(author))
                .wasSuccessful(true)
                .build();
    }

    public ServiceResponse<Author> update(int id, Author author) {
        var authorToUpdate = getById(id);

        if (authorToUpdate == null) {
            return ServiceResponse.<Author>builder()
                    .message("The author with given ID was not found.")
                    .build();
        }

        authorToUpdate.setName(author.getName());
        authorToUpdate.setSurname(author.getSurname());
        authorToUpdate.setEmail(author.getEmail());

        var violations = validator.validate(authorToUpdate);

        if (!violations.isEmpty()) {
            return ServiceResponse.createInvalidResponse(violations);
        }

        if (repository.existsByEmailAndIdNot(authorToUpdate.getEmail(), authorToUpdate.getId())) {
            return ServiceResponse.<Author>builder()
                    .message("The author with given email already exists.")
                    .build();
        }

        return ServiceResponse.<Author>builder()
                .data(repository.save(authorToUpdate))
                .wasSuccessful(true)
                .build();
    }

    public ServiceResponse<Author> delete(int id) {
        if (!repository.existsById(id)) {
            return ServiceResponse.<Author>builder()
                    .wasSuccessful(true)
                    .message("No author to delete.")
                    .build();
        }

        repository.deleteById(id);

        return ServiceResponse.<Author>builder()
                .wasSuccessful(true)
                .message("The author was successfully deleted.")
                .build();
    }

    public Author mapFromDto(AuthorDto dto) {
        if (dto == null) return null;

        return Author.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .email(dto.getEmail())
                .build();
    }
}
