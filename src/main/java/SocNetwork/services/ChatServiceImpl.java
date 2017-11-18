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

import java.util.*;


@Service
public class ChatServiceImpl implements ChatService{

    private ChatRoomRepository chatRoomRepository;
    private MessageRepository messageRepository;
    private UserRepository userRepository;
    private UserService userService;

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

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @Override
    public List getLastMessagesByEmail(String email) throws UserNotFoundException{

        User user = userService.getUserByEmail(email);

        Set<ChatRoom> rooms = user.getChatRooms();
        ArrayList<Message> lastMessages = new ArrayList<>();
        for (ChatRoom room : rooms){

            // This will stay here for some time just as reminder for me (& you):
            // Next line is needed because user was loaded with depth = 1
            // "The default depth of 1 implies that related node or relationship entities will be loaded and have their properties set,
            // but none of their related entities will be populated." <----- official docs
            room = chatRoomRepository.findOne(room.getId());

            SortedSet<Message> messages = room.getMessages();
            lastMessages.add(messages.last());
        }
        return lastMessages;
    }

    @Override
    public List getMessagesWithUserByEmail(String whoRequestsEmail, Long withWhomId, Integer offset, Integer count)
            throws UserNotFoundException {

        User user = userService.getUserByEmail(whoRequestsEmail);

        Long commonRoomId = chatRoomRepository.findCommon(user.getId(), withWhomId);
        if (commonRoomId == null) {
            return null;
        }

        ChatRoom room = chatRoomRepository.findOne(commonRoomId);
        ArrayList<Message> messages = new ArrayList<>(room.getMessages());

        return messages.subList(
                Math.max(0, offset),
                Math.min(offset + count, messages.size()));
    }

    @Override
    public void sendMessageByEmail(String senderEmail, Long recipientId, Message message)
            throws UserNotFoundException {

        User sender = userService.getUserByEmail(senderEmail);
        User recipient = userService.getUserById(recipientId);

        Long commonChatRoomId = chatRoomRepository.findCommon(sender.getId(), recipientId);
        ChatRoom commonChatRoom;

        if (commonChatRoomId == null) {

            //persist new chatroom
            commonChatRoom = new ChatRoom();
            commonChatRoom = chatRoomRepository.save(commonChatRoom);

            //persist sender's reference to chatroom
            Set<ChatRoom> senderChatRooms = sender.getChatRooms();
            senderChatRooms.add(commonChatRoom);
            sender.setChatRooms(senderChatRooms);
            userRepository.save(sender);

            //persist recipient's reference to chatroom
            Set<ChatRoom> recipientChatRooms = recipient.getChatRooms();
            recipientChatRooms.add(commonChatRoom);
            recipient.setChatRooms(recipientChatRooms);
            userRepository.save(recipient);

        } else {
            commonChatRoom = chatRoomRepository.findOne(commonChatRoomId);
        }

        //persist new message
        message.setSenderId(sender.getId());
        message.setRecipientId(recipient.getId());
        message.setDatetime(new Date());
        message = messageRepository.save(message);

        //persist room's reference to message
        SortedSet<Message> messages = commonChatRoom.getMessages();
        messages.add(message);
        commonChatRoom.setMessages(messages);
        chatRoomRepository.save(commonChatRoom);
    }

}
