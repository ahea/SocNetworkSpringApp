package SocNetwork.services;

import SocNetwork.exceptions.EmailExistsException;
import SocNetwork.exceptions.UserNotFoundException;
import SocNetwork.models.nodeEntities.Role;
import SocNetwork.models.nodeEntities.User;
import SocNetwork.repositories.RoleRepository;
import SocNetwork.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder encoder;
    private RoleRepository roleRepository;
    private LanguageService languageService;

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

    @Autowired
    public void setLanguageService(LanguageService languageService) {
        this.languageService = languageService;
    }

    @Override
    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) throws UserNotFoundException{

        User user = userRepository.findOne(id);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        user.setLanguages(languageService.getLanguagesByUserId(user.getId()));
        return user;
    }

    @Override
    public User getUserByEmail(String email) throws UserNotFoundException{

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        user.setLanguages(languageService.getLanguagesByUserId(user.getId()));
        return user;
    }

    @Override
    public void saveUser(User user) throws EmailExistsException {

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new EmailExistsException("This email is already used");
        }
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("USER"));
        user.setRoles(roles);
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void updateUser(String email, User updatedUser) throws UserNotFoundException {

        User user = getUserByEmail(email);
        user.setName(       updatedUser.getName());
        user.setAbout(      updatedUser.getAbout());
        user.setAge(        updatedUser.getAge());
        user.setCountry(    updatedUser.getCountry());
        user.setGender(     updatedUser.getGender());
        user.setPassword(   updatedUser.getPassword());
        languageService.updateLanguages(user, updatedUser.getLanguages());
        userRepository.save(user);
    }

    @Override
    public void addToFriendList (String whoAddsEmail, Long whoIsAddedId)
            throws UserNotFoundException {

        User whoAdds    = getUserByEmail(whoAddsEmail);
        User whoIsAdded = getUserById(whoIsAddedId);

        Set<User> friendList = whoAdds.getFriendList();
        if (friendList == null) {
            friendList = new HashSet<>();
        }
        friendList.add(whoIsAdded);
        whoAdds.setFriendList(friendList);
        userRepository.save(whoAdds);
    }

    @Override
    public void removeFromFriendList(String whoRemovesEmail, Long whoIsRemovedId)
            throws UserNotFoundException {

        User whoRemoves =   getUserByEmail(whoRemovesEmail);
        User whoIsRemoved = getUserById(whoIsRemovedId);

        Set<User> friendList = whoRemoves.getFriendList();
        if (friendList == null) {
            return;
        }
        friendList.remove(whoIsRemoved);
        whoRemoves.setFriendList(friendList);
        userRepository.save(whoRemoves);
    }

    @Override
    public void addToBlackList(User whoBlocks, User whoIsBlocked)
            throws UserNotFoundException {

        if (whoBlocks == null)
            throw new UserNotFoundException("User not found");
        Set<User> blackList = whoBlocks.getBlackList();
        if (blackList == null)
            blackList = new HashSet<>();
        if (blackList.contains(whoIsBlocked))
            return;
        blackList.add(whoIsBlocked);
        whoBlocks.setBlackList(blackList);
        userRepository.save(whoBlocks);
    }

    @Override
    public void removeFromBlackList(User whoRemoves, User whoIsRemoved)
            throws UserNotFoundException {

        if (whoIsRemoved == null)
            throw new UserNotFoundException("User not found");
        Set<User> blackList = whoRemoves.getBlackList();
        if (blackList == null || !blackList.contains(whoIsRemoved))
            throw new UserNotFoundException("User is not in friendlist");
        blackList.remove(whoIsRemoved);
        whoRemoves.setBlackList(blackList);
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
                result.add(friend.hideCredentials().hideRelationships());
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
                result.add(friend.hideCredentials().hideRelationships());
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
                result.add(friend.hideCredentials().hideRelationships());
            }
        }
        return result;
    }

    @Override
    public Set getBlackList(User user){

        Set<User> set = user.getBlackList();
        for (User blockedUser : set){
            blockedUser.hideCredentials().hideRelationships();
        }
        return set;
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
