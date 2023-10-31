package pl.edu.pw.pamiwapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.pamiwapi.dtos.PublisherDto;
import pl.edu.pw.pamiwapi.models.Publisher;
import pl.edu.pw.pamiwapi.services.PublisherService;
import pl.edu.pw.pamiwapi.utils.ServiceResponse;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PublisherController {
    private final PublisherService service;

    @Autowired
    public PublisherController(PublisherService service) {
        this.service = service;
    }

    @GetMapping("/publishers")
    public ResponseEntity<ServiceResponse<List<PublisherDto>>> getAll() {
        List<PublisherDto> data = new ArrayList<>();

        service.getAll().forEach((x) -> data.add(PublisherDto.mapToDto(x)));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ServiceResponse.<List<PublisherDto>>builder()
                        .data(data)
                        .wasSuccessful(true)
                        .build());
    }

    @GetMapping("/publishers/{id}")
    public ResponseEntity<ServiceResponse<PublisherDto>> get(@PathVariable int id) {
        var data = service.getById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ServiceResponse.<PublisherDto>builder()
                        .wasSuccessful(true)
                        .data(PublisherDto.mapToDto(data))
                        .build());
    }

    @PostMapping("/publishers")
    public ResponseEntity<ServiceResponse<PublisherDto>> create(@RequestBody PublisherDto dto) {
        var response = service.create(service.mapFromDto(dto));

        if (!response.isWasSuccessful()) {
            return getInvalidResponse(response);
        }

        return getValidResponse(response);
    }

    @PutMapping("/publishers/{id}")
    public ResponseEntity<ServiceResponse<PublisherDto>> update(@PathVariable int id, @RequestBody PublisherDto dto) {
        var response = service.update(id, service.mapFromDto(dto));

        if (!response.isWasSuccessful()) {
            return getInvalidResponse(response);
        }

        return getValidResponse(response);
    }

    @DeleteMapping("/publishers/{id}")
    public ResponseEntity<ServiceResponse<PublisherDto>> delete(@PathVariable int id) {
        service.delete(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }

    private ResponseEntity<ServiceResponse<PublisherDto>> getInvalidResponse(ServiceResponse<Publisher> response) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ServiceResponse.<PublisherDto>builder()
                        .wasSuccessful(response.isWasSuccessful())
                        .message(response.getMessage())
                        .build());
    }

    private ResponseEntity<ServiceResponse<PublisherDto>> getValidResponse(ServiceResponse<Publisher> response) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ServiceResponse.<PublisherDto>builder()
                        .wasSuccessful(true)
                        .data(PublisherDto.mapToDto(response.getData()))
                        .build());
    }
}
