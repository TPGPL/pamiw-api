package pl.edu.pw.pamiwapi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pw.pamiwapi.model.domain.Publisher;

@Repository
public interface PublisherRepository extends CrudRepository<Publisher,Integer> {
}
