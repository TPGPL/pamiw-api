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
@RequestMapping("/publishers")
@CrossOrigin
public class PublisherController {
    private final PublisherService service;

    @Autowired
    public PublisherController(PublisherService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ServiceResponse<List<PublisherDto>>> getAll() {
        List<PublisherDto> data = new ArrayList<>();

        service.getAll().forEach((x) -> data.add(PublisherDto.mapToDto(x)));

        return ResponseEntity.status(HttpStatus.OK)
                .body(ServiceResponse.<List<PublisherDto>>builder()
                        .data(data)
                        .wasSuccessful(true)
                        .message(data.isEmpty() ? "No data to fetch." : null)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse<PublisherDto>> get(@PathVariable int id) {
        var data = service.getById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ServiceResponse.<PublisherDto>builder()
                        .wasSuccessful(true)
                        .data(PublisherDto.mapToDto(data))
                        .message(data == null ? "No data to fetch." : null)
                        .build());
    }

    @PostMapping
    public ResponseEntity<ServiceResponse<PublisherDto>> create(@RequestBody PublisherDto dto) {
        var response = service.create(service.mapFromDto(dto));

        if (!response.isWasSuccessful()) {
            return getInvalidResponse(response);
        }

        return getValidResponse(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse<PublisherDto>> update(@PathVariable int id, @RequestBody PublisherDto dto) {
        var response = service.update(id, service.mapFromDto(dto));

        if (!response.isWasSuccessful()) {
            return getInvalidResponse(response);
        }

        return getValidResponse(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResponse<PublisherDto>> delete(@PathVariable int id) {
        var response = service.delete(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ServiceResponse.<PublisherDto>builder()
                        .wasSuccessful(true)
                        .message(response.getMessage())
                        .build());
    }

    private ResponseEntity<ServiceResponse<PublisherDto>> getInvalidResponse(ServiceResponse<Publisher> response) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ServiceResponse.<PublisherDto>builder()
                        .message(response.getMessage())
                        .build());
    }

    private ResponseEntity<ServiceResponse<PublisherDto>> getValidResponse(ServiceResponse<Publisher> response) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ServiceResponse.<PublisherDto>builder()
                        .wasSuccessful(true)
                        .data(PublisherDto.mapToDto(response.getData()))
                        .build());
    }
}
