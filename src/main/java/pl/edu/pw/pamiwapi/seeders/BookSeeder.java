package pl.edu.pw.pamiwapi.seeders;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pw.pamiwapi.models.Book;
import pl.edu.pw.pamiwapi.services.AuthorService;
import pl.edu.pw.pamiwapi.services.BookService;
import pl.edu.pw.pamiwapi.services.PublisherService;

import java.sql.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static pl.edu.pw.pamiwapi.seeders.Properties.DATA_SEED;
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
        this.faker = new Faker(new Random(DATA_SEED));
    }

    public void seed() {
        var currData = service.getAll();

        if (currData.iterator().hasNext()) {
            return;
        }

        int maxValue = Math.max(NO_OF_DATA - 5, NO_OF_DATA / 2);

        for (int i = 0; i < NO_OF_DATA; i++) {
            var book = Book.builder()
                    .publisher(publisherService.getById(faker.random().nextInt(maxValue)))
                    .author(authorService.getById(faker.random().nextInt(maxValue)))
                    .title(faker.book().title())
                    .isbn(faker.regexify("[0-9]{13}"))
                    .pageCount(faker.random().nextInt(10, 1000))
                    .releaseDate(new Date(faker.date().past(365, TimeUnit.DAYS).getTime()))
                    .build();

            service.create(book);
        }
    }
}
