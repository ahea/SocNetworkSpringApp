package SocNetwork.services;

import SocNetwork.exceptions.EmailExistsException;
import SocNetwork.models.User;

/**
 * Created by aleksei on 11.02.17.
 */
public interface UserService {

    Iterable<User> getAllUsers();

    User getUserById(Long id);

    User getUserByEmail(String email);

    void saveUser(User user) throws EmailExistsException;

    void deleteUserById(Long id);

    void deleteUser(User user);

    void deleteAllUsers();

}


