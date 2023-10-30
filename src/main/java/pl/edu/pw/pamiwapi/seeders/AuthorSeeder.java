package pl.edu.pw.pamiwapi.seeders;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pw.pamiwapi.models.Author;
import pl.edu.pw.pamiwapi.services.AuthorService;

import static pl.edu.pw.pamiwapi.seeders.Properties.NO_OF_DATA;

@Component
public class AuthorSeeder {
    private final AuthorService service;
    private final Faker faker;

    @Autowired
    public AuthorSeeder(AuthorService service) {
        this.service = service;
        this.faker = new Faker();
    }

    public void seed() {
        for (int i = 0; i < NO_OF_DATA; i++) {
            var author = Author.builder()
                    .name(faker.name().firstName())
                    .surname(faker.name().lastName())
                    .email(faker.internet().emailAddress())
                    .build();

            service.create(author);
        }
    }
}
