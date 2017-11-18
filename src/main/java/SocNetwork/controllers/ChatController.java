package SocNetwork.controllers;

import SocNetwork.exceptions.UserNotFoundException;
import SocNetwork.models.enums.ServerResponse;
import SocNetwork.models.nodeEntities.Message;
import SocNetwork.models.nodeEntities.User;
import SocNetwork.services.ChatService;
import SocNetwork.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


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

    @RequestMapping(value = "/api/lastMessages", method = RequestMethod.GET)
    public ResponseEntity<List> getLastMessages(Principal principal)
            throws UserNotFoundException {

        String email = principal.getName();
        return new ResponseEntity<>(chatService.getLastMessagesByEmail(email), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/messages", method = RequestMethod.GET)
    public ResponseEntity<List> getMessagesFromUser (@Param("id") Long id,
                                                     @Param("offset") Integer offset,
                                                     @Param("count") Integer count,
                                                     Principal principal)
            throws UserNotFoundException {

        String email = principal.getName();
        return new ResponseEntity<>(chatService.getMessagesWithUserByEmail(email, id, offset, count), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/message", method = RequestMethod.POST)
    public ResponseEntity<Integer> sendMessage(@RequestBody Message message,
                                               @Param("id") Long id,
                                               Principal principal)
            throws UserNotFoundException {

        String email = principal.getName();
        chatService.sendMessageByEmail(email, id, message);
        return new ResponseEntity<>(ServerResponse.SUCCESS.ordinal(), HttpStatus.OK);
    }

}
