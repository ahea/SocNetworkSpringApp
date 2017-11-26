package SocNetwork.controllers;

import SocNetwork.exceptions.UserNotFoundException;
import SocNetwork.models.nodeEntities.User;
import SocNetwork.models.enums.ServerResponse;
import SocNetwork.services.LanguageService;
import SocNetwork.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@RestController
public class ProfileController {

    private final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    private UserService userService;
    private LanguageService languageService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @Autowired
    public void setLanguageService(LanguageService languageService){
        this.languageService = languageService;
    }

    @RequestMapping(value = "/api/profile/me", method = RequestMethod.GET)
    public User getMyProfile(Principal principal) throws UserNotFoundException {

        String email = principal.getName();
        logger.info("[Request] /api/profile/me [Email] " + email);
        return userService.getUserByEmail(email)
                .hideRelationships();
    }

    @RequestMapping(value = "/api/profile/{id}", method = RequestMethod.GET)
    public User getProfileById(@PathVariable long id, Principal principal) throws UserNotFoundException {

        logger.info("[Request] /api/profile/" + id + " [Email] " + principal.getName());
        return userService.getUserById(id)
                .hideCredentials()
                .hideRelationships()
                .hideChatRooms()
                .hideBlackList();
    }

    @RequestMapping(value = "/api/profile/edit", method = RequestMethod.POST)
    public ResponseEntity<Integer> updateProfile(@RequestBody User updatedUser,
                                                 Principal principal) throws UserNotFoundException {

        String email = principal.getName();
        logger.info("[Request] /api/profile/edit [Email] " + principal.getName());
        userService.updateUser(email, updatedUser);
        return new ResponseEntity<>(ServerResponse.SUCCESS.ordinal(), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/profile/{id}/add", method = RequestMethod.POST)
    public ResponseEntity<Integer> addFriend(@PathVariable long id, Principal principal)
            throws UserNotFoundException {

        String email = principal.getName();
        logger.info("[Request] /api/profile/" + id + "/add [Email] " + email);
        userService.addToFriendList(email, id);
        return new ResponseEntity<>(ServerResponse.SUCCESS.ordinal(), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/profile/{id}/delete", method = RequestMethod.POST)
    public ResponseEntity<Integer> removeFriend(@PathVariable long id, Principal principal)
            throws UserNotFoundException {

        String email = principal.getName();
        logger.info("[Request] /api/profile/" + id + "/delete [Email] " + email);
        userService.removeFromFriendList(email, id);
        return new ResponseEntity<>(ServerResponse.SUCCESS.ordinal(), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/profile/{id}/block", method = RequestMethod.POST)
    public ResponseEntity<Integer> blockUser(@PathVariable long id, Principal principal)
        throws UserNotFoundException {

        String email = principal.getName();
        logger.info("[Request] /api/profile/" + id + "/block [Email] " + email);
        userService.addToBlackList(email, id);
        return new ResponseEntity<>(ServerResponse.SUCCESS.ordinal(), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/profile/{id}/unblock", method = RequestMethod.POST)
    public ResponseEntity<Integer> unblockUser(@PathVariable long id, Principal principal)
            throws UserNotFoundException{

        String email = principal.getName();
        logger.info("[Request] /api/profile/" + id + "/unblock [Email] " + email);
        userService.removeFromBlackList(email, id);
        return new ResponseEntity<>(ServerResponse.SUCCESS.ordinal(), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/profile/{id}/friends", method = RequestMethod.GET)
    public ResponseEntity<List> getFriends(@PathVariable long id, Principal principal)
            throws UserNotFoundException {

        logger.info("[Request] /api/profile/" + id + "/friends [Email] " + principal.getName());
        Set friends = userService.getFriendsById(id);
        return new ResponseEntity<>(new ArrayList(friends), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/profile/{id}/subscribers", method = RequestMethod.GET)
    public ResponseEntity<List> getSubsribers(@PathVariable long id, Principal principal)
        throws UserNotFoundException{

        logger.info("[Request] /api/profile/" + id + "/subscribers [Email] " + principal.getName());
        Set subscribers = userService.getSubscribersById(id);
        return new ResponseEntity<>(new ArrayList(subscribers), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/profile/{id}/subscriptions", method = RequestMethod.GET)
    public ResponseEntity<List> getSubscriptions(@PathVariable long id, Principal principal)
        throws UserNotFoundException{

        logger.info("[Request] /api/profile/" + id + "/subscriptions [Email] " + principal.getName());
        Set subscriptions = userService.getSubscriptionsById(id);
        return new ResponseEntity<>(new ArrayList(subscriptions), HttpStatus.OK);
    }

    @RequestMapping(value = "api/profile/blacklist", method = RequestMethod.GET)
    public ResponseEntity<List> getBlacklist(Principal principal)
            throws UserNotFoundException{

        String email = principal.getName();
        logger.info("[Request] /api/profile/blacklist [Email] " + email);
        Set blacklist = userService.getBlackListByEmail(email);
        return new ResponseEntity<>(new ArrayList(blacklist), HttpStatus.OK);
    }

}
