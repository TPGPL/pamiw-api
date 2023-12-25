package pl.edu.pw.pamiwapi.seeders;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pw.pamiwapi.model.domain.Publisher;
import pl.edu.pw.pamiwapi.services.PublisherService;

import java.util.Random;

import static pl.edu.pw.pamiwapi.seeders.Properties.DATA_SEED;
import static pl.edu.pw.pamiwapi.seeders.Properties.NO_OF_DATA;

@Component
public class PublisherSeeder {
    private final PublisherService service;
    private final Faker faker;

    @Autowired
    public PublisherSeeder(PublisherService service) {
        this.service = service;
        this.faker = new Faker(new Random(DATA_SEED));
    }

    public void seed() {
        var currData = service.getAll();

        if (currData.iterator().hasNext()) {
            return;
        }

        for (int i = 0; i < NO_OF_DATA; i++) {
            var publisher = Publisher.builder()
                    .name(faker.book().publisher())
                    .build();

            service.create(publisher);
        }
    }
}
