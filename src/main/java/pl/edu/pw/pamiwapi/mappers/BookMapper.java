package pl.edu.pw.pamiwapi.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pw.pamiwapi.dtos.BookDto;
import pl.edu.pw.pamiwapi.models.Book;
import pl.edu.pw.pamiwapi.services.AuthorService;
import pl.edu.pw.pamiwapi.services.PublisherService;

@Component
public class BookMapper implements EntityMapper<Book, BookDto> {
    private final AuthorService authorService;
    private final PublisherService publisherService;

    @Autowired
    public BookMapper(AuthorService authorService, PublisherService publisherService) {
        this.authorService = authorService;
        this.publisherService = publisherService;
    }

    @Override
    public BookDto mapToDto(Book entity) {
        if (entity == null) return null;

        return BookDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .authorId(entity.getAuthor().getId())
                .publisherId(entity.getPublisher().getId())
                .pageCount(entity.getPageCount())
                .releaseDate(entity.getReleaseDate())
                .isbn(entity.getIsbn())
                .build();
    }

    @Override
    public Book mapToEntity(BookDto dto) {
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
