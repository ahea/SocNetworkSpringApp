package SocNetwork.services;

import SocNetwork.exceptions.UserNotFoundException;
import SocNetwork.models.nodeEntities.Message;
import SocNetwork.models.nodeEntities.User;

import java.util.List;


public interface ChatService {

    List getLastMessagesByEmail(String email) throws UserNotFoundException;

    List getMessagesWithUserByEmail(String whoRequestsEmail, Long withWhomId, Integer offset, Integer count)
            throws UserNotFoundException;

    void sendMessageByEmail(String senderEmail, Long recipientId, Message message) throws UserNotFoundException;

}
