package tp.eni_store.dao;

import tp.eni_store.bo.User;


public interface UserDAO {
    User selectByUsername(String username);
    User selectById(String id);
    void save(User user);
}
