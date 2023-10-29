package pl.edu.pw.pamiwapi.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pw.pamiwapi.models.Author;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorDto {
    private int id;
    private String name;
    private String surname;
    private String email;
    @Builder.Default
    private List<BookDto> books = new ArrayList<>();

    public static AuthorDto mapToDto(Author author) {
        if (author == null) return null;

        AuthorDto dto = AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .email(author.getEmail())
                .build();

        for (var book : author.getBooks()) {
            dto.books.add(BookDto.mapToDto(book));
        }

        return dto;
    }
}
