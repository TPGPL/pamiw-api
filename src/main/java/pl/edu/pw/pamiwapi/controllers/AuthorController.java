package pl.edu.pw.pamiwapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.pamiwapi.model.dtos.AuthorDto;
import pl.edu.pw.pamiwapi.mappers.AuthorMapper;
import pl.edu.pw.pamiwapi.model.domain.Author;
import pl.edu.pw.pamiwapi.services.AuthorService;
import pl.edu.pw.pamiwapi.utils.ServiceResponse;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/authors")
@CrossOrigin
public class AuthorController {
    private final AuthorService service;
    private final AuthorMapper mapper;

    @Autowired
    public AuthorController(AuthorService service, AuthorMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<ServiceResponse<List<AuthorDto>>> getAll() {
        List<AuthorDto> data = new ArrayList<>();

        service.getAll().forEach((x) -> data.add(mapper.mapToDto(x)));

        return ResponseEntity.status(HttpStatus.OK)
                .body(ServiceResponse.<List<AuthorDto>>builder()
                        .wasSuccessful(true)
                        .data(data)
                        .message(data.isEmpty() ? "No data to fetch." : null)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse<AuthorDto>> get(@PathVariable int id) {
        var data = service.getById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ServiceResponse.<AuthorDto>builder()
                        .wasSuccessful(true)
                        .data(mapper.mapToDto(data))
                        .message(data == null ? "No data to fetch." : null)
                        .build());
    }

    @PostMapping
    public ResponseEntity<ServiceResponse<AuthorDto>> create(@RequestBody AuthorDto dto) {
        var response = service.create(mapper.mapToEntity(dto));

        if (!response.isWasSuccessful()) {
            return getInvalidResponse(response);
        }

        return getValidResponse(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse<AuthorDto>> update(@PathVariable int id, @RequestBody AuthorDto dto) {
        var response = service.update(id, mapper.mapToEntity(dto));

        if (!response.isWasSuccessful()) {
            return getInvalidResponse(response);
        }

        return getValidResponse(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResponse<AuthorDto>> delete(@PathVariable int id) {
        var response = service.delete(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ServiceResponse.<AuthorDto>builder()
                        .wasSuccessful(true)
                        .message(response.getMessage())
                        .build());
    }

    private ResponseEntity<ServiceResponse<AuthorDto>> getInvalidResponse(ServiceResponse<Author> response) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ServiceResponse.<AuthorDto>builder()
                        .message(response.getMessage())
                        .build());
    }

    private ResponseEntity<ServiceResponse<AuthorDto>> getValidResponse(ServiceResponse<Author> response) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ServiceResponse.<AuthorDto>builder()
                        .wasSuccessful(true)
                        .data(mapper.mapToDto(response.getData()))
                        .build());
    }
}
