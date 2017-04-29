package SocNetwork.services;

import SocNetwork.exceptions.UserNotFoundException;
import SocNetwork.models.nodeEntities.Message;
import SocNetwork.models.nodeEntities.User;

/**
 * Created by aleksei on 29.04.17.
 */
public interface ChatService {

    void sendMessage(User sender, User recipient, Message message)
        throws UserNotFoundException;


}
