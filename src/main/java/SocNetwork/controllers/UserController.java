package SocNetwork.controllers;

import SocNetwork.exceptions.EmailExistsException;
import SocNetwork.models.User;
import SocNetwork.models.enums.ServerResponse;
import SocNetwork.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by aleksei on 11.02.17.
 */
@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = "/api/register", method = RequestMethod.POST)
    public ResponseEntity<ServerResponse> register(@RequestBody User user) {
        try {
            userService.saveUser(user);
        } catch (EmailExistsException e) {
            return new ResponseEntity<>(ServerResponse.EMAIL_EXISTS, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(ServerResponse.USER_CREATED, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/secured", method = RequestMethod.GET)
    public String checkSecured(){
        return "Authorization is ok";
    }

    @RequestMapping(value = "/api/free", method = RequestMethod.GET)
    public String checkFree(){
        return "Free from authorization";
    }

}
