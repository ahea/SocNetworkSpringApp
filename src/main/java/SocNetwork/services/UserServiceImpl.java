package SocNetwork.services;

import SocNetwork.exceptions.EmailExistsException;
import SocNetwork.exceptions.UserNotFoundException;
import SocNetwork.models.Role;
import SocNetwork.models.User;
import SocNetwork.repositories.RoleRepository;
import SocNetwork.repositories.UserRepository;
import org.neo4j.ogm.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jTemplate;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;

/**
 * Created by aleksei on 11.02.17.
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder encoder;
    private RoleRepository roleRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Autowired
    public void setEncoder(PasswordEncoder encoder){
        this.encoder = encoder;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Override
    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User getUserByIdHide(Long id) throws UserNotFoundException {
        User user = userRepository.findOne(id);
        if (user == null) throw new UserNotFoundException("User not found");
        user.setPassword(null);
        user.setFriendList(null);
        user.setBlackList(null);
        return user;
    }

    @Override
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public  User getUserByEmailHide(String email){
        User user = userRepository.findByEmail(email);
        user.setPassword(null);
        user.setFriendList(null);
        user.setBlackList(null);
        return user;
    }

    @Override
    public void saveUser(User user) throws EmailExistsException {
        if (getUserByEmail(user.getEmail()) != null)
            throw new EmailExistsException("This email is already used");
        Role role = roleRepository.findByName("USER");
        Set<Role> set = new HashSet<>();
        set.add(role);
        user.setRoles(set);
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void addToFriendList (User whoAdds, User whoIsAdded)
            throws UserNotFoundException{
        if (whoIsAdded == null)
            throw new UserNotFoundException("User not found");
        Set<User> friendList = whoAdds.getFriendList();
        if (friendList == null)
            friendList = new HashSet<>();
        if (friendList.contains(whoIsAdded))
            return;
        friendList.add(whoIsAdded);
        whoAdds.setFriendList(friendList);
        userRepository.save(whoAdds);
    }

    @Override
    public void removeFromFriendList(User whoRemoves, User whoIsRemoved)
            throws UserNotFoundException{
        if (whoIsRemoved == null)
            throw new UserNotFoundException("User not found");
        Set<User> friendList = whoRemoves.getFriendList();
        if (friendList == null || !friendList.contains(whoIsRemoved))
            throw new UserNotFoundException("User is not in friendlist");
        friendList.remove(whoIsRemoved);
        whoRemoves.setFriendList(friendList);
        userRepository.save(whoRemoves);
    }

    @Override
    public Set getFriends(User user) throws UserNotFoundException {
        if (user == null)
            throw new UserNotFoundException("User not found");
        Set<User> set = user.getFriendList();
        Set<User> result = new HashSet<>();
        for (User friend  : set) {
            if (friend.getFriendList().contains(user)) {
                friend.setFriendList(null);
                friend.setBlackList(null);
                friend.setPassword(null);
                result.add(friend);
            }
        }
        return result;
    }

    @Override
    public Set getSubscribers(User user) throws UserNotFoundException {
        if (user == null)
            throw new UserNotFoundException("User not found");
        Collection<User> incFriends = userRepository.getIncomingFriendsById(user.getId());
        Set<User> friendList = user.getFriendList();
        Set<User> result = new HashSet<>();
        for (User friend : incFriends){
            if (!friendList.contains(friend)) {
                friend.setFriendList(null);
                friend.setBlackList(null);
                friend.setPassword(null);
                result.add(friend);
            }
        }
        return result;
    }

    @Override
    public Set getSubscriptions(User user) throws UserNotFoundException{
        if (user == null)
            throw new UserNotFoundException("User not found");
        Set<User> set = user.getFriendList();
        Set<User> result = new HashSet<>();
        for (User friend  : set) {
            if (!friend.getFriendList().contains(user)) {
                friend.setFriendList(null);
                friend.setBlackList(null);
                friend.setPassword(null);
                result.add(friend);
            }
        }
        return result;
    }

    @Override
    public void deleteUserById(Long id){
        userRepository.delete(id);
    }

    @Override
    public void deleteUser(User user){
        userRepository.delete(user);
    }

    @Override
    public void deleteAllUsers(){
        userRepository.deleteAll();
    }

}
