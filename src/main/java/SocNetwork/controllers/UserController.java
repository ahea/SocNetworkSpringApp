package SocNetwork.controllers;

import SocNetwork.exceptions.EmailExistsException;
import SocNetwork.exceptions.UserNotFoundException;
import SocNetwork.models.User;
import SocNetwork.models.enums.ServerResponse;
import SocNetwork.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import javax.servlet.http.HttpServletRequest;


/**
 * Created by aleksei on 11.02.17.
 */
@RestController
public class UserController {

    private UserService userService;
    private TokenStore tokenStore;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @Autowired
    public void setTokenStore(TokenStore tokenStore){
        this.tokenStore = tokenStore;
    }

    @RequestMapping(value = "/api/register", method = RequestMethod.POST)
    public ResponseEntity<Integer> register(@RequestBody User user) throws EmailExistsException{
        userService.saveUser(user);
        return new ResponseEntity<>(ServerResponse.SUCCESS.ordinal(), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/logout", method = RequestMethod.GET)
    public void logout(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null){
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }
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
