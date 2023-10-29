package pl.edu.pw.pamiwapi.dtos;

import lombok.*;

import java.sql.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private int id;
    private String title;
    private int authorId;
    private int publisherId;
    private int pageCount;
    private Date releaseDate;
}
