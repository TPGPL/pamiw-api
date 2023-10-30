package pl.edu.pw.pamiwapi.seeders;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pw.pamiwapi.models.Book;
import pl.edu.pw.pamiwapi.services.AuthorService;
import pl.edu.pw.pamiwapi.services.BookService;
import pl.edu.pw.pamiwapi.services.PublisherService;

import java.sql.Date;
import java.util.Random;

import static pl.edu.pw.pamiwapi.seeders.Properties.NO_OF_DATA;

@Component
public class BookSeeder {
    private final BookService service;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private final Faker faker;

    @Autowired
    public BookSeeder(BookService service, AuthorService authorService, PublisherService publisherService) {
        this.service = service;
        this.authorService = authorService;
        this.publisherService = publisherService;
        this.faker = new Faker();
    }

    public void seed() {
        int maxValue = Math.max(NO_OF_DATA - 5, NO_OF_DATA / 2);
        var rand = new Random();

        for (int i = 0; i < NO_OF_DATA; i++) {
            var book = Book.builder()
                    .publisher(publisherService.getById(rand.nextInt(maxValue)))
                    .author(authorService.getById(rand.nextInt(maxValue)))
                    .title(faker.book().title())
                    .isbn("1234567894444")
                    .pageCount(rand.nextInt(10, 1000))
                    .releaseDate(new Date(1111100000))
                    .build();

                    service.create(book);
        }
    }
}
