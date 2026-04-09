package tp.eni_store.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import tp.eni_store.bo.User;

import java.util.Optional;

public interface UserMongoRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}