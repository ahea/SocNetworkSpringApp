package SocNetwork.services;

import SocNetwork.exceptions.UserNotFoundException;
import SocNetwork.models.nodeEntities.ChatRoom;
import SocNetwork.models.nodeEntities.Message;
import SocNetwork.models.nodeEntities.User;
import SocNetwork.repositories.ChatRoomRepository;
import SocNetwork.repositories.MessageRepository;
import SocNetwork.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

/**
 * Created by aleksei on 29.04.17.
 */

@Service
public class ChatServiceImpl implements ChatService{

    private ChatRoomRepository chatRoomRepository;
    private MessageRepository messageRepository;
    private UserRepository userRepository;

    @Autowired
    public void setChatRoomRepository(ChatRoomRepository chatRoomRepository){
        this.chatRoomRepository = chatRoomRepository;
    }

    @Autowired
    public void setMessageRepository(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void sendMessage(User sender, User recipient, Message message)
            throws UserNotFoundException{
        if (sender == null || recipient == null) throw new UserNotFoundException("User not found");
        Collection<ChatRoom> commonChatRooms = chatRoomRepository.findCommon(
                sender.getId(), recipient.getId());
        ChatRoom commonChatRoom;
        if (commonChatRooms.isEmpty()){
            commonChatRoom = new ChatRoom();
            commonChatRoom = chatRoomRepository.save(commonChatRoom);
            Set<ChatRoom> senderChatRooms = sender.getChatRooms();
            Set<ChatRoom> recipientChatRooms = recipient.getChatRooms();
            senderChatRooms.add(commonChatRoom);
            recipientChatRooms.add(commonChatRoom);
            sender.setChatRooms(senderChatRooms);
            recipient.setChatRooms(recipientChatRooms);
            userRepository.save(sender);
            userRepository.save(recipient);
        } else {
            commonChatRoom = commonChatRooms.iterator().next();
        }
        message.setSenderId(sender.getId());
        message.setRecipientId(recipient.getId());
        Set<Message> messages = commonChatRoom.getMessages();
        messages.add(message);
        commonChatRoom.setMessages(messages);
        chatRoomRepository.save(commonChatRoom);
    }
}
