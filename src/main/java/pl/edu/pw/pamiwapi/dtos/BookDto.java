package pl.edu.pw.pamiwapi.dtos;

import lombok.*;
import pl.edu.pw.pamiwapi.models.Book;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private int id;
    private String title;
    private int authorId;
    private int publisherId;
    private int pageCount;
    private Date releaseDate;
    private String isbn;
}
