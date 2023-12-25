package pl.edu.pw.pamiwapi.seeders;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pw.pamiwapi.models.Author;
import pl.edu.pw.pamiwapi.services.AuthorService;

import java.util.Random;

import static pl.edu.pw.pamiwapi.seeders.Properties.NO_OF_DATA;
import static pl.edu.pw.pamiwapi.seeders.Properties.DATA_SEED;

@Component
public class AuthorSeeder {
    private final AuthorService service;
    private final Faker faker;

    @Autowired
    public AuthorSeeder(AuthorService service) {
        this.service = service;
        this.faker = new Faker(new Random(DATA_SEED));
    }

    public void seed() {
        var currData = service.getAll();

        if (currData.iterator().hasNext()) {
            return;
        }
        
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
