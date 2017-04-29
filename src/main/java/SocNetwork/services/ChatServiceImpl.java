package SocNetwork.services;

import SocNetwork.repositories.ChatRoomRepository;
import SocNetwork.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by aleksei on 29.04.17.
 */

@Service
public class ChatServiceImpl implements ChatService{

    private ChatRoomRepository chatRoomRepository;
    private MessageRepository messageRepository;

    @Autowired
    public void setChatRoomRepository(ChatRoomRepository chatRoomRepository){
        this.chatRoomRepository = chatRoomRepository;
    }

    @Autowired
    public void setMessageRepository(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

}
