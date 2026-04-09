package tp.eni_store.dao.mongo;

import org.springframework.stereotype.Repository;
import tp.eni_store.bo.User;
import tp.eni_store.dao.UserDAO;

@Repository
public class UserDAOMongo implements UserDAO {

    private final UserMongoRepository userRepo;

    public UserDAOMongo(UserMongoRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User selectByUsername(String username) {
        return this.userRepo.findByUsername(username).orElse(null);
    }

    @Override
    public User selectById(String id) {
        return this.userRepo.findById(id).orElse(null);
    }

    @Override
    public void save(User user) {
        this.userRepo.save(user);
    }
}
