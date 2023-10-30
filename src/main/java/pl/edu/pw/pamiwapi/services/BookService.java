package pl.edu.pw.pamiwapi.services;

import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pw.pamiwapi.dtos.BookDto;
import pl.edu.pw.pamiwapi.models.Book;
import pl.edu.pw.pamiwapi.repositories.BookRepository;
import pl.edu.pw.pamiwapi.utils.ServiceResponse;

@Service
public class BookService {
    private final BookRepository repository;
    private final PublisherService publisherService;
    private final AuthorService authorService;
    private final Validator validator;

    @Autowired
    public BookService(BookRepository repository, Validator validator, PublisherService publisherService, AuthorService authorService) {
        this.validator = validator;
        this.repository = repository;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }

    public Book getById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Iterable<Book> getAll() {
        return repository.findAll();
    }

    public ServiceResponse<Book> create(Book book) {
        var violations = validator.validate(book);

        if (!violations.isEmpty()) {
            return ServiceResponse.createInvalidResponse(violations);
        }

        return ServiceResponse.<Book>builder()
                .wasSuccessful(true)
                .data(repository.save(book))
                .build();
    }

    public ServiceResponse<Book> update(int id, Book book) {
        var bookToUpdate = getById(id);

        if (bookToUpdate == null) {
            return ServiceResponse.<Book>builder()
                    .message("The book with given ID was not found.")
                    .build();
        }

        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setPublisher(book.getPublisher());
        bookToUpdate.setIsbn(book.getIsbn());
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setReleaseDate(book.getReleaseDate());
        bookToUpdate.setPageCount(book.getPageCount());

        var violations = validator.validate(bookToUpdate);

        if (!violations.isEmpty()) {
            return ServiceResponse.createInvalidResponse(violations);
        }

        return ServiceResponse.<Book>builder()
                .wasSuccessful(true)
                .data(repository.save(bookToUpdate))
                .build();
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

    public Book mapFromDto(BookDto dto) {
        if (dto == null) return null;

        return Book.builder()
                .title(dto.getTitle())
                .isbn(dto.getIsbn())
                .releaseDate(dto.getReleaseDate())
                .pageCount(dto.getPageCount())
                .author(authorService.getById(dto.getAuthorId()))
                .publisher(publisherService.getById(dto.getPublisherId()))
                .build();
    }


}
