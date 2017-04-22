package SocNetwork.controllers;

import SocNetwork.exceptions.UserNotFoundException;
import SocNetwork.models.User;
import SocNetwork.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

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

}
