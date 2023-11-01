package pl.edu.pw.pamiwapi.seeders;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pw.pamiwapi.models.Publisher;
import pl.edu.pw.pamiwapi.services.PublisherService;

import static pl.edu.pw.pamiwapi.seeders.Properties.NO_OF_DATA;

@Component
public class PublisherSeeder {
    private final PublisherService service;
    private final Faker faker;

    @Autowired
    public PublisherSeeder(PublisherService service) {
        this.service = service;
        this.faker = new Faker();
    }

    public void seed() {
        for (int i = 0; i < NO_OF_DATA; i++) {
            var publisher = Publisher.builder()
                    .name(faker.book().publisher())
                    .build();

            service.create(publisher);
        }
    }
}
