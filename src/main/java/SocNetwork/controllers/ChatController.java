package SocNetwork.controllers;

import SocNetwork.exceptions.UserNotFoundException;
import SocNetwork.models.enums.ServerResponse;
import SocNetwork.models.nodeEntities.Message;
import SocNetwork.models.nodeEntities.User;
import SocNetwork.services.ChatService;
import SocNetwork.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Created by aleksei on 29.04.17.
 */

@RestController
public class ChatController {

    private ChatService chatService;
    private UserService userService;

    @Autowired
    public void setChatService(ChatService chatService){
        this.chatService = chatService;
    }

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = "api/messages", method = RequestMethod.GET)
    public ResponseEntity<List> getLastMessages(Principal principal)
            throws UserNotFoundException{
        String email = principal.getName();
        User user = userService.getUserByEmail(email);
        List list = chatService.getLastMessages(user);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "api/messages/{id}", method = RequestMethod.POST)
    public ResponseEntity<Integer> sendMessage(@RequestBody Message message,
                                               @PathVariable long id,
                                               Principal principal) throws UserNotFoundException{
        String email = principal.getName();
        User sender = userService.getUserByEmail(email);
        User recipient = userService.getUserById(id);
        chatService.sendMessage(sender, recipient, message);
        return new ResponseEntity<>(ServerResponse.SUCCESS.ordinal(), HttpStatus.OK);
    }

}
