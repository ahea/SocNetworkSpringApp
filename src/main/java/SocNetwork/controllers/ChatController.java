package SocNetwork.controllers;

import SocNetwork.exceptions.UserNotFoundException;
import SocNetwork.models.enums.ServerResponse;
import SocNetwork.models.nodeEntities.Message;
import SocNetwork.services.ChatService;
import SocNetwork.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;


@RestController
public class ChatController {

    private final Logger logger = LoggerFactory.getLogger(ChatController.class);

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

    @RequestMapping(value = "/api/message", method = RequestMethod.GET)
    public ResponseEntity<List> getMessagesFromUser (@Param("id") Long id,
                                                     @Param("offset") Integer offset,
                                                     @Param("count") Integer count,
                                                     Principal principal)
            throws UserNotFoundException {

        String email = principal.getName();
        logger.info("[Request] /api/message GET " +
                "[Email] " + email + " [Id] " + id + " [Offset] " + offset + " [Count] " + count);
        return new ResponseEntity<>(chatService.getMessagesWithUserByEmail(email, id, offset, count), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/message", method = RequestMethod.POST)
    public ResponseEntity<Integer> sendMessage(@RequestBody Message message,
                                               @Param("id") Long id,
                                               Principal principal)
            throws UserNotFoundException {

        String email = principal.getName();
        logger.info("[Request] /api/message POST [Email] " + email + " [Id] " + id);
        chatService.sendMessageByEmail(email, id, message);
        return new ResponseEntity<>(ServerResponse.SUCCESS.ordinal(), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/conversations", method = RequestMethod.GET)
    public ResponseEntity<Map> getLastMessages(Principal principal)
            throws UserNotFoundException {

        String email = principal.getName();
        logger.info("[Request] /api/lastMessages [Email] " + email);
        return new ResponseEntity<>(chatService.getLastMessagesByEmail(email), HttpStatus.OK);
    }

}
