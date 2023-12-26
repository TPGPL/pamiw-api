package pl.edu.pw.pamiwapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.edu.pw.pamiwapi.seeders.AuthorSeeder;
import pl.edu.pw.pamiwapi.seeders.BookSeeder;
import pl.edu.pw.pamiwapi.seeders.PublisherSeeder;
import pl.edu.pw.pamiwapi.seeders.UserSeeder;

@Component
public class DataLoader implements CommandLineRunner {
    private final AuthorSeeder authorSeeder;
    private final PublisherSeeder publisherSeeder;
    private final BookSeeder bookSeeder;
    private final UserSeeder userSeeder;

    @Autowired
    public DataLoader(AuthorSeeder authorSeeder, PublisherSeeder publisherSeeder, BookSeeder bookSeeder, UserSeeder userSeeder) {
        this.authorSeeder = authorSeeder;
        this.publisherSeeder = publisherSeeder;
        this.bookSeeder = bookSeeder;
        this.userSeeder = userSeeder;
    }

    @Override
    public void run(String... args) {
        authorSeeder.seed();
        publisherSeeder.seed();
        bookSeeder.seed();
        userSeeder.seed();
    }
}
