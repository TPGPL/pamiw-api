package pl.edu.pw.pamiwapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pw.pamiwapi.models.Publisher;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublisherDto {
    private int id;
    private String name;
    @Builder.Default
    private List<BookDto> books = new ArrayList<>();

    public static PublisherDto mapToDto(Publisher publisher) {
        if (publisher == null) return null;

        PublisherDto dto = PublisherDto.builder()
                .id(publisher.getId())
                .name(publisher.getName())
                .build();

        for (var book : publisher.getBooks()) {
            dto.books.add(BookDto.mapToDto(book));
        }

        return dto;
    }
}
