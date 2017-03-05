package SocNetwork.services;

import SocNetwork.exceptions.EmailExistsException;
import SocNetwork.exceptions.UserNotFoundException;
import SocNetwork.models.User;

/**
 * Created by aleksei on 11.02.17.
 */

public interface UserService {

    Iterable<User> getAllUsers();

    User getUserById(Long id);

    User getUserByIdHidePassword(Long id) throws UserNotFoundException;

    User getUserByEmail(String email);

    User getUserByEmailHidePassword(String email);

    void saveUser(User user) throws EmailExistsException;

    void deleteUserById(Long id);

    void deleteUser(User user);

    void deleteAllUsers();

}


