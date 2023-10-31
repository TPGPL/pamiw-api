package pl.edu.pw.pamiwapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.pamiwapi.dtos.BookDto;
import pl.edu.pw.pamiwapi.models.Book;
import pl.edu.pw.pamiwapi.services.BookService;
import pl.edu.pw.pamiwapi.utils.ServiceResponse;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {
    private final BookService service;

    @Autowired
    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("/books")
    public ResponseEntity<ServiceResponse<List<BookDto>>> getAll() {
        List<BookDto> data = new ArrayList<>();

        service.getAll().forEach((x) -> data.add(BookDto.mapToDto(x)));

        return ResponseEntity.status(HttpStatus.OK)
                .body(ServiceResponse.<List<BookDto>>builder()
                        .wasSuccessful(true)
                        .data(data)
                        .message(data.isEmpty() ? "No data to fetch." : null)
                        .build());
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<ServiceResponse<BookDto>> get(@PathVariable int id) {
        var data = service.getById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ServiceResponse.<BookDto>builder()
                        .wasSuccessful(true)
                        .data(BookDto.mapToDto(data))
                        .message(data == null ? "No data to fetch." : null)
                        .build());
    }

    @PostMapping("/books")
    public ResponseEntity<ServiceResponse<BookDto>> create(@RequestBody BookDto dto) {
        var response = service.create(service.mapFromDto(dto));

        if (!response.isWasSuccessful()) {
            return getInvalidResponse(response);
        }

        return getValidResponse(response);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<ServiceResponse<BookDto>> update(@PathVariable int id, @RequestBody BookDto dto) {
        var response = service.update(id, service.mapFromDto(dto));

        if (!response.isWasSuccessful()) {
            return getInvalidResponse(response);
        }

        return getValidResponse(response);
    }

    @DeleteMapping("/books/{id}")
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
                        .data(BookDto.mapToDto(response.getData()))
                        .build());
    }
}
