package pl.edu.pw.pamiwapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.pamiwapi.model.dtos.BookDto;
import pl.edu.pw.pamiwapi.mappers.BookMapper;
import pl.edu.pw.pamiwapi.model.domain.Book;
import pl.edu.pw.pamiwapi.services.BookService;
import pl.edu.pw.pamiwapi.model.ServiceResponse;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
@CrossOrigin
public class BookController {
    private final BookService service;
    private final BookMapper mapper;

    @Autowired
    public BookController(BookService service, BookMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<ServiceResponse<List<BookDto>>> getAll() {
        List<BookDto> data = new ArrayList<>();

        service.getAll().forEach((x) -> data.add(mapper.mapToDto(x)));

        return ResponseEntity.status(HttpStatus.OK)
                .body(ServiceResponse.<List<BookDto>>builder()
                        .wasSuccessful(true)
                        .data(data)
                        .message(data.isEmpty() ? "No data to fetch." : null)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse<BookDto>> get(@PathVariable int id) {
        var data = service.getById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ServiceResponse.<BookDto>builder()
                        .wasSuccessful(true)
                        .data(mapper.mapToDto(data))
                        .message(data == null ? "No data to fetch." : null)
                        .build());
    }

    @PostMapping
    public ResponseEntity<ServiceResponse<BookDto>> create(@RequestBody BookDto dto) {
        var response = service.create(mapper.mapToEntity(dto));

        if (!response.isWasSuccessful()) {
            return getInvalidResponse(response);
        }

        return getValidResponse(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse<BookDto>> update(@PathVariable int id, @RequestBody BookDto dto) {
        var response = service.update(id, mapper.mapToEntity(dto));

        if (!response.isWasSuccessful()) {
            return getInvalidResponse(response);
        }

        return getValidResponse(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResponse<BookDto>> delete(@PathVariable int id) {
        var response = service.delete(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ServiceResponse.<BookDto>builder()
                        .wasSuccessful(true)
                        .message(response.getMessage())
                        .build());
    }

    private ResponseEntity<ServiceResponse<BookDto>> getInvalidResponse(ServiceResponse<Book> response) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ServiceResponse.<BookDto>builder()
                        .message(response.getMessage())
                        .build());
    }

    private ResponseEntity<ServiceResponse<BookDto>> getValidResponse(ServiceResponse<Book> response) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ServiceResponse.<BookDto>builder()
                        .wasSuccessful(true)
                        .data(mapper.mapToDto(response.getData()))
                        .build());
    }
}
