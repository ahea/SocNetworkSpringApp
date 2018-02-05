package SocNetwork.services;

import SocNetwork.exceptions.UserNotFoundException;
import SocNetwork.models.nodeEntities.Message;
import SocNetwork.models.nodeEntities.User;

import java.util.List;
import java.util.Map;


public interface ChatService {

    List getMessagesWithUserByEmail(String whoRequestsEmail, Long withWhomId, Integer offset, Integer count)
            throws UserNotFoundException;

    void sendMessageByEmail(String senderEmail, Long recipientId, Message message) throws UserNotFoundException;

    Map getLastMessagesByEmail(String email) throws UserNotFoundException;
}
