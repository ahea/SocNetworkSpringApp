package SocNetwork.services;

import SocNetwork.exceptions.EmailExistsException;
import SocNetwork.exceptions.UserNotFoundException;
import SocNetwork.models.nodeEntities.Role;
import SocNetwork.models.nodeEntities.User;
import SocNetwork.repositories.RoleRepository;
import SocNetwork.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
            logger.info("[getUserById] User not found in userRepository [Id] " + id);
            throw new UserNotFoundException("User not found");
        }
        user.setLanguages(languageService.getLanguagesByUserId(user.getId()));
        logger.info("[getUserById] User loaded from userRepository: \n" + user.toString());
        return user;
    }

    @Override
    public User getUserByEmail(String email) throws UserNotFoundException {

        User user = userRepository.findByEmail(email);
        if (user == null) {
            logger.info("[getUserByEmail] User not found in userRepository [Email] " + email);
            throw new UserNotFoundException("User not found");
        }
        user.setLanguages(languageService.getLanguagesByUserId(user.getId()));
        logger.info("[getUserByEmail] User loaded from userRepository: \n" + user.toString());
        return user;
    }

    @Override
    public void saveUser(User user) throws EmailExistsException {

        if (userRepository.findByEmail(user.getEmail()) != null) {
            logger.info("[saveUser] Email is already used [Email] " + user.getEmail());
            throw new EmailExistsException("This email is already used");
        }
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("USER"));
        user.setRoles(roles);
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        logger.info("[saveUser] User saved: \n" + user.toString());
    }

    @Override
    public void updateUser(String email, User updatedUser) throws UserNotFoundException {

        User user = getUserByEmail(email);
        logger.info("[updateUser] Updating user: \n" + user.toString());

        String name = updatedUser.getName();
        if (name != null) {
            user.setName(name.isEmpty() ? null : name);
        }

        String about = updatedUser.getAbout();
        if (about != null) {
            user.setAbout(about.isEmpty() ? null : about);
        }

        if (updatedUser.getAge() != null) {
            user.setAge(updatedUser.getAge());
        }

        if (updatedUser.getCountry() != null) {
            user.setCountry(updatedUser.getCountry());
        }

        if (updatedUser.getGender() != null) {
            user.setGender(updatedUser.getGender());
        }

        String password = updatedUser.getPassword();
        if (password != null) {
            user.setPassword(password.isEmpty() ? null : password);
        }

        if (updatedUser.getLanguages() != null) {
            languageService.updateLanguages(user, updatedUser.getLanguages());
        }
        userRepository.save(user);
        logger.info("[updateUser] Updated user \n" + user.toString());
    }

    @Override
    public void addToFriendList (String whoAddsEmail, Long whoIsAddedId)
            throws UserNotFoundException {

        User whoAdds    = getUserByEmail(whoAddsEmail);
        User whoIsAdded = getUserById(whoIsAddedId);
        logger.info("[addToFriendList] Adding to friends: [whoAddsEmail] " + whoAddsEmail + " [whoIsAddedId] " + whoIsAddedId);

        Set<User> friendList = whoAdds.getFriendList();
        if (friendList == null) {
            friendList = new HashSet<>();
        }
        friendList.add(whoIsAdded);
        whoAdds.setFriendList(friendList);
        userRepository.save(whoAdds);
        logger.info("[addToFriendList] FriendList updated for: \n" + whoAdds.toString());
    }

    @Override
    public void removeFromFriendList(String whoRemovesEmail, Long whoIsRemovedId)
            throws UserNotFoundException {

        User whoRemoves =   getUserByEmail(whoRemovesEmail);
        User whoIsRemoved = getUserById(whoIsRemovedId);
        logger.info("[removeFromFriendList] Removing from friends: [whoRemovesEmail] " + whoRemovesEmail + " [whoIsRemovedId] " + whoIsRemovedId);

        Set<User> friendList = whoRemoves.getFriendList();
        if (friendList == null) {
            logger.info("[removeFromFriendList] friendlist is empty: [whoRemovesEmail] " + whoRemovesEmail);
            return;
        }
        friendList.remove(whoIsRemoved);
        whoRemoves.setFriendList(friendList);
        userRepository.save(whoRemoves);
        logger.info("[removeFromFriendList] FriendList updated for: \n" + whoRemoves.toString());
    }

    @Override
    public void addToBlackList(String whoBlocksEmail, Long whoIsBlockedId)
            throws UserNotFoundException {

        User whoBlocks = getUserByEmail(whoBlocksEmail);
        User whoIsBlocked = getUserById(whoIsBlockedId);
        logger.info("[addToBlackList] Adding to blacklist: [whoBlocksEmail] " + whoBlocksEmail + " [whoIsBlockedId] " + whoIsBlockedId);

        Set<User> blackList = whoBlocks.getBlackList();
        if (blackList == null) {
            blackList = new HashSet<>();
        }
        blackList.add(whoIsBlocked);
        whoBlocks.setBlackList(blackList);
        userRepository.save(whoBlocks);
        logger.info("[addToBlackList] Blacklist updated for: \n" + whoBlocks.toString());
    }

    @Override
    public void removeFromBlackList(String whoUnblocksEmail, Long whoIsUnblockedId)
            throws UserNotFoundException {

        User whoUnblocks = getUserByEmail(whoUnblocksEmail);
        User whoIsUnblocked = getUserById(whoIsUnblockedId);
        logger.info("[removeFromBlackList] Removing from blacklist: [whoUnblocksEmail] " + whoUnblocksEmail + " [whoIsUnblockedId] " + whoIsUnblockedId);

        Set<User> blacklist = whoUnblocks.getBlackList();
        if (blacklist == null) {
            logger.info("[removeFromBlackList] Blacklist is empty: [whoUnblocksEmail] " + whoUnblocksEmail);
            return;
        }
        blacklist.remove(whoIsUnblocked);
        whoUnblocks.setBlackList(blacklist);
        userRepository.save(whoUnblocks);
        logger.info("[addToBlackList] Blacklist updated for: \n" + whoUnblocks.toString());
    }

    @Override
    public Set getFriendsById(Long id) throws UserNotFoundException {

        User user = getUserById(id);

        Set<User> friends = new HashSet<>();
        for (User friend  : user.getFriendList()) {
            if (friend.getFriendList().contains(user)) {
                friends.add(friend
                        .hideCredentials()
                        .hideRelationships()
                        .hideChatRooms()
                        .hideBlackList());
            }
        }
        logger.info("[getFriendsById] [Id] " + id + " [friends] " + friends );
        return friends;
    }

    @Override
    public Set getSubscribersById(Long id) throws UserNotFoundException {

        User user = getUserById(id);

        Set<User> friendList = user.getFriendList();
        Collection<User> incFriends = userRepository.getIncomingFriendsById(user.getId());

        Set<User> subscribers = new HashSet<>();
        for (User incFriend : incFriends){
            if (!friendList.contains(incFriend)) {
                subscribers.add(incFriend
                        .hideCredentials()
                        .hideRelationships()
                        .hideChatRooms()
                        .hideBlackList());
            }
        }
        logger.info("[getSubscribersById] [Id] " + id + " [subscribers] " + subscribers);
        return subscribers;
    }

    @Override
    public Set getSubscriptionsById(Long id) throws UserNotFoundException{

        User user = getUserById(id);

        Set<User> friendlist = user.getFriendList();
        Set<User> subscriptions = new HashSet<>();
        for (User friend  : friendlist) {
            if (!friend.getFriendList().contains(user)) {
                subscriptions.add(friend
                        .hideCredentials()
                        .hideRelationships()
                        .hideChatRooms()
                        .hideBlackList());
            }
        }
        return subscriptions;
    }

    @Override
    public Set getBlackListByEmail(String email) throws UserNotFoundException{

        User user = getUserByEmail(email);

        Set<User> blacklist = user.getBlackList();
        for (User blockedUser : blacklist) {
            blockedUser
                    .hideCredentials()
                    .hideRelationships()
                    .hideChatRooms()
                    .hideBlackList();
        }
        logger.info("[getBlackListByEmail] [Email] " + email + " [blacklist] " + blacklist);
        return blacklist;
    }

    @Override
    public void deleteUserById(Long id){}

    @Override
    public void deleteUser(User user){}

    @Override
    public void deleteAllUsers() {}

}
