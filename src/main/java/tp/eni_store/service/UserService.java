package tp.eni_store.service;

import org.springframework.stereotype.Service;
import tp.eni_store.bo.User;
import tp.eni_store.dao.UserDAO;
import tp.eni_store.response.ApiResponse;


@Service
public class UserService
{

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
