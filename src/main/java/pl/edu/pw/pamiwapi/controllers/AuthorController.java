package pl.edu.pw.pamiwapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.pamiwapi.dtos.AuthorDto;
import pl.edu.pw.pamiwapi.models.Author;
import pl.edu.pw.pamiwapi.services.AuthorService;
import pl.edu.pw.pamiwapi.utils.ServiceResponse;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AuthorController {
    private final AuthorService service;

    @Autowired
    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @GetMapping("/authors")
    public ResponseEntity<ServiceResponse<List<AuthorDto>>> getAll() {
        List<AuthorDto> data = new ArrayList<>();

        service.getAll().forEach((x) -> data.add(AuthorDto.mapToDto(x)));

        return ResponseEntity
                .status(data.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK)
                .body(ServiceResponse.<List<AuthorDto>>builder()
                        .wasSuccessful(true)
                        .data(data)
                        .build());
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<ServiceResponse<AuthorDto>> get(@PathVariable int id) {
        var data = service.getById(id);

        return ResponseEntity
                .status(data == null ? HttpStatus.NO_CONTENT : HttpStatus.OK)
                .body(ServiceResponse.<AuthorDto>builder()
                        .wasSuccessful(true)
                        .data(AuthorDto.mapToDto(data))
                        .build());
    }

    @PostMapping("/authors")
    public ResponseEntity<ServiceResponse<AuthorDto>> create(@RequestBody AuthorDto dto) {
        var response = service.create(service.mapFromDto(dto));

        if (!response.isWasSuccessful()) {
            return getInvalidResponse(response);
        }

        return getValidResponse(response);
    }

    @PutMapping("/authors/{id}")
    public ResponseEntity<ServiceResponse<AuthorDto>> update(@PathVariable int id, @RequestBody AuthorDto dto) {
        var response = service.update(id, service.mapFromDto(dto));

        if (!response.isWasSuccessful()) {
            return getInvalidResponse(response);
        }

        return getValidResponse(response);
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity<ServiceResponse<AuthorDto>> delete(@PathVariable int id) {
        service.delete(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }

    private ResponseEntity<ServiceResponse<AuthorDto>> getInvalidResponse(ServiceResponse<Author> response) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ServiceResponse.<AuthorDto>builder()
                        .wasSuccessful(response.isWasSuccessful())
                        .message(response.getMessage())
                        .build());
    }

    private ResponseEntity<ServiceResponse<AuthorDto>> getValidResponse(ServiceResponse<Author> response) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ServiceResponse.<AuthorDto>builder()
                        .wasSuccessful(true)
                        .data(AuthorDto.mapToDto(response.getData()))
                        .build());
    }
}
