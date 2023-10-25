package pl.edu.pw.pamiwapi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pw.pamiwapi.models.Book;

@Repository
public interface BookRepository extends CrudRepository<Book,Integer> {
}
