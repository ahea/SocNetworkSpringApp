package SocNetwork.services;

import SocNetwork.exceptions.EmailExistsException;
import SocNetwork.exceptions.UserNotFoundException;
import SocNetwork.models.nodeEntities.User;

import java.util.Set;


public interface UserService {

    Iterable<User> getAllUsers();

    User getUserById(Long id) throws UserNotFoundException;

    User getUserByEmail(String email) throws UserNotFoundException;

    void saveUser(User user) throws EmailExistsException;

    void updateUser(String email, User updatedUser) throws UserNotFoundException;

    void addToFriendList(String whoAddsEmail, Long whoIsAddedId) throws UserNotFoundException;

    void removeFromFriendList(String whoRemovesEmail, Long whoIsRemovedId) throws UserNotFoundException;

    void addToBlackList(User whoBlocks, User whoIsBlocked) throws UserNotFoundException;

    void removeFromBlackList(User whoRemoves, User whoIsRemoved) throws UserNotFoundException;

    Set getFriends(User user) throws UserNotFoundException;

    Set getSubscribers(User user) throws UserNotFoundException;

    Set getSubscriptions(User user) throws UserNotFoundException;

    Set getBlackList(User user);

    void deleteUserById(Long id);

    void deleteUser(User user);

    void deleteAllUsers();

}


