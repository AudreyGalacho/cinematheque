package fr.eni.filmotheque.bll.user;

import fr.eni.filmotheque.bo.User;

import java.util.List;

public interface UserService {
    List<User> getUsersList();
    User getUserByUsername(String username);

}
