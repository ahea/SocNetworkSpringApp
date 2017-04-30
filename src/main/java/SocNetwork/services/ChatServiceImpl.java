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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

    @Override
    public List getLastMessages(User user) throws UserNotFoundException{
        if (user == null) throw new UserNotFoundException("User not found");
        Set<ChatRoom> rooms = user.getChatRooms();
        List<Message> result = new ArrayList<>();
        for (ChatRoom room : rooms){
            //next line is needed because user was loaded with depth = 1
            room = chatRoomRepository.findOne(room.getId());
            List<Message> messages = room.getMessages();
            result.add(messages.get(messages.size() - 1));
        }
        return result;
    }

    @Override
    public void sendMessage(User sender, User recipient, Message message)
            throws UserNotFoundException{
        if (sender == null || recipient == null) throw new UserNotFoundException("User not found");
        Collection<Long> commonChatRooms = chatRoomRepository.findCommon(
                sender.getId(), recipient.getId());
        ChatRoom commonChatRoom;
        if (commonChatRooms.isEmpty()) {

            commonChatRoom = new ChatRoom();
            commonChatRoom = chatRoomRepository.save(commonChatRoom);

            Set<ChatRoom> senderChatRooms = sender.getChatRooms();
            senderChatRooms.add(commonChatRoom);
            sender.setChatRooms(senderChatRooms);
            userRepository.save(sender);

            Set<ChatRoom> recipientChatRooms = recipient.getChatRooms();
            recipientChatRooms.add(commonChatRoom);
            recipient.setChatRooms(recipientChatRooms);
            userRepository.save(recipient);

        } else {
            commonChatRoom = chatRoomRepository.findOne(
                    commonChatRooms.iterator().next());
        }

        message.setSenderId(sender.getId());
        message.setRecipientId(recipient.getId());
        message = messageRepository.save(message);

        List<Message> messages = commonChatRoom.getMessages();
        messages.add(message);
        commonChatRoom.setMessages(messages);
        chatRoomRepository.save(commonChatRoom);
    }
}
