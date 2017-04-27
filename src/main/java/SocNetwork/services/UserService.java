package SocNetwork.services;

import SocNetwork.exceptions.EmailExistsException;
import SocNetwork.exceptions.UserNotFoundException;
import SocNetwork.models.User;

import java.util.List;
import java.util.Set;

/**
 * Created by aleksei on 11.02.17.
 */

public interface UserService {

    Iterable<User> getAllUsers();

    User getUserById(Long id);

    User getUserByIdHide(Long id) throws UserNotFoundException;

    User getUserByEmail(String email);

    User getUserByEmailHide(String email);

    void saveUser(User user) throws EmailExistsException;

    void addToFriendList(User whoAdds, User whoIsAdded) throws UserNotFoundException;

    void removeFromFriendList(User whoRemoves, User whoIsRemoved) throws UserNotFoundException;

    void addToBlackList(User whoBlocks, User whoIsBlocked) throws UserNotFoundException;

    void removeFromBlackList(User whoRemoves, User whoIsRemoved) throws UserNotFoundException;

    Set getFriends(User user) throws UserNotFoundException;

    Set getSubscribers(User user) throws UserNotFoundException;

    Set getSubscriptions(User user) throws UserNotFoundException;

    void deleteUserById(Long id);

    void deleteUser(User user);

    void deleteAllUsers();

}


