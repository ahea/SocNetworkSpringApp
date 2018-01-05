package SocNetwork.services;

import SocNetwork.exceptions.UserNotFoundException;
import SocNetwork.models.nodeEntities.ChatRoom;
import SocNetwork.models.nodeEntities.Message;
import SocNetwork.models.nodeEntities.User;
import SocNetwork.repositories.ChatRoomRepository;
import SocNetwork.repositories.MessageRepository;
import SocNetwork.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ChatServiceImpl implements ChatService{

    private final Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);

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

    // This will stay here for some time just as reminder for me (& you):
    // Next line is needed because user was loaded with depth = 1
    // "The default depth of 1 implies that related node or relationship entities will be loaded and have their properties set,
    // but none of their related entities will be populated."

    @Override
    public List getMessagesWithUserByEmail(String whoRequestsEmail, Long withWhomId, Integer offset, Integer count)
            throws UserNotFoundException {

        User user = userService.getUserByEmail(whoRequestsEmail);

        Long commonRoomId = chatRoomRepository.findCommon(user.getId(), withWhomId);
        if (commonRoomId == null) {
            logger.info("[getMessagesWithUserByEmail] No messages found: [whoRequestsEmail] "
                    + whoRequestsEmail + " [withWhomId] " + withWhomId);
            return null;
        }

        ChatRoom room = chatRoomRepository.findOne(commonRoomId);
        ArrayList<Message> messages = new ArrayList<>(room.getMessages());

        logger.info("[getMessagesWithUserByEmail] Messages retrieved for [whoRequestsEmail] "
                + whoRequestsEmail + " [withWhomId] " + withWhomId);
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

            logger.info("[sendMessageByEmail] No chatRoom found for [senderId] " + sender.getId() + " [recipientId] " + recipientId);

            //persist new chatroom
            commonChatRoom = new ChatRoom();
            commonChatRoom = chatRoomRepository.save(commonChatRoom);
            logger.info("[sendMessageByEmail] New chatRoom created: [chatRoomId]" + commonChatRoom.getId() +
                    " [senderId] " + sender.getId() + " [recipientId] " + recipientId);

            //persist sender's reference to chatroom
            Set<ChatRoom> senderChatRooms = sender.getChatRooms();
            senderChatRooms.add(commonChatRoom);
            sender.setChatRooms(senderChatRooms);
            userRepository.save(sender);
            logger.info("[sendMessageByEmail] new chatroom was added to sender's list: \n" + sender.toString());

            //persist recipient's reference to chatroom
            Set<ChatRoom> recipientChatRooms = recipient.getChatRooms();
            recipientChatRooms.add(commonChatRoom);
            recipient.setChatRooms(recipientChatRooms);
            userRepository.save(recipient);
            logger.info("[sendMessageByEmail] new chatroom was added to recipient's list: \n" + recipient.toString());

        } else {
            commonChatRoom = chatRoomRepository.findOne(commonChatRoomId);
            logger.info("[sendMessageByEmail] Common chatRooom found: [chatRoomId] " + commonChatRoom.getId() +
                    " [senderId] " + sender.getId() + " [recipientId] " + recipientId);
        }

        //persist new message
        message.setSenderId(sender.getId());
        message.setRecipientId(recipient.getId());
        message.setDatetime(new Date());
        message = messageRepository.save(message);
        logger.info("[sendMessageByEmail] New message was saved [messageId]" + message.getId() + " [chatRoomId] " + commonChatRoom.getId() +
                        " [senderId] " + sender.getId() + " [recipientId] " + recipientId);

        //persist room's reference to message
        SortedSet<Message> messages = commonChatRoom.getMessages();
        messages.add(message);
        commonChatRoom.setMessages(messages);
        chatRoomRepository.save(commonChatRoom);
        logger.info("[sendMessageByEmail] Room's reference to message persisted: [chatRoomId] " + commonChatRoom.getId());
    }

}
