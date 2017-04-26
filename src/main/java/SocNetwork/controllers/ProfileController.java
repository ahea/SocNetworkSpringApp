package SocNetwork.controllers;

import SocNetwork.exceptions.UserNotFoundException;
import SocNetwork.models.User;
import SocNetwork.models.enums.ServerResponse;
import SocNetwork.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * Created by aleksei on 05.03.17.
 */
@RestController
public class ProfileController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = "/api/profile/me", method = RequestMethod.GET)
    public User getMyProfile(Principal principal) {
        String email = principal.getName();
        return userService.getUserByEmailHidePassword(email);
    }

    @RequestMapping(value = "/api/profile/{id}", method = RequestMethod.GET)
    public User getProfile(@PathVariable long id) throws UserNotFoundException {
        return userService.getUserByIdHidePassword(id);
    }

    @RequestMapping(value = "/api/profile/{id}/add", method = RequestMethod.POST)
    public ResponseEntity<Integer> addFriend(@PathVariable long id, Principal principal)
            throws UserNotFoundException{
        String email = principal.getName();
        User whoAdds = userService.getUserByEmail(email);
        User whoIsAdded = userService.getUserById(id);
        userService.addToFriendList(whoAdds, whoIsAdded);
        return new ResponseEntity<>(ServerResponse.SUCCESS.ordinal(), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/profile/{id}/delete", method = RequestMethod.POST)
    public ResponseEntity<Integer> removeFriend(@PathVariable long id, Principal principal)
            throws UserNotFoundException{
        String email = principal.getName();
        User whoRemoves = userService.getUserByEmail(email);
        User whoIsRemoved = userService.getUserById(id);
        userService.removeFromFriendList(whoRemoves, whoIsRemoved);
        return new ResponseEntity<>(ServerResponse.SUCCESS.ordinal(), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/profile/{id}/friends")
    public ResponseEntity<List> getFriends(@PathVariable long id)
            throws UserNotFoundException {
        User user = userService.getUserById(id);
        List list = userService.getFriends(user);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/profile/{id}/subscribers")
    public ResponseEntity<List> getSubsribers(@PathVariable long id)
        throws UserNotFoundException{
        User user = userService.getUserById(id);
        List list = userService.getSubscribers(user);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
