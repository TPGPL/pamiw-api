package pl.edu.pw.pamiwapi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pw.pamiwapi.models.Publisher;

@Repository
public interface PublisherRepository extends CrudRepository<Publisher,Integer> {
}
