package friends.dao;

import friends.dto.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, String>, PersonRepoCustom{
    Person findByEmail(String email);
}
