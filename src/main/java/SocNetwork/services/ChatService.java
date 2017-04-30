package SocNetwork.services;

import SocNetwork.exceptions.UserNotFoundException;
import SocNetwork.models.nodeEntities.Message;
import SocNetwork.models.nodeEntities.User;

import java.util.List;

/**
 * Created by aleksei on 29.04.17.
 */
public interface ChatService {

    List getLastMessages(User user) throws UserNotFoundException;

    List getMessagesWithUser(User whoReceives, User whoSent, int offset, int count)
            throws UserNotFoundException;

    void sendMessage(User sender, User recipient, Message message)
        throws UserNotFoundException;


}
