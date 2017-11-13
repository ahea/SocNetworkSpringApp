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

    void addToBlackList(String whoBlocksEmail, Long whoIsBlockedId) throws UserNotFoundException;

    void removeFromBlackList(String whoUnblocksEmail, Long whoIsUnblockedId) throws UserNotFoundException;

    Set getFriendsById(Long id) throws UserNotFoundException;

    Set getSubscribersById(Long id) throws UserNotFoundException;

    Set getSubscriptionsById(Long id) throws UserNotFoundException;

    Set getBlackListByEmail(String email) throws UserNotFoundException;

    void deleteUserById(Long id);

    void deleteUser(User user);

    void deleteAllUsers();

}


