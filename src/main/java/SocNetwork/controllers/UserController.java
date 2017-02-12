package SocNetwork.controllers;

import SocNetwork.exceptions.EmailExistsException;
import SocNetwork.models.User;
import SocNetwork.models.enums.ServerResponse;
import SocNetwork.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    @RequestMapping(value="/api/register", method=RequestMethod.POST)
    public ResponseEntity<ServerResponse> register(@RequestBody User user) throws EmailExistsException{
        userService.saveUser(user);
        return new ResponseEntity<ServerResponse>(ServerResponse.USER_CREATED,
                HttpStatus.OK);
    }

}
