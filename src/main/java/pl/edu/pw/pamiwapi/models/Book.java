package pl.edu.pw.pamiwapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "publisher_id", referencedColumnName = "id")
    private Publisher publisher;
    private int pageCount;
    private Date releaseDate;
}
