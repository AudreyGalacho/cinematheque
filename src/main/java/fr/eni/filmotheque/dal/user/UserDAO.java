package fr.eni.filmotheque.dal.user;

import fr.eni.filmotheque.bo.User;

import java.util.List;

public interface UserDAO {


    List<User> getUsersList();
    User getUserByUsername(String username);
    void insertUser(User user);

    User getUserById(int idUser);
}
