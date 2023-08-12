package com.volunteernet.volunteernet.services.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.volunteernet.volunteernet.dto.message.MessageResponseDto;
import com.volunteernet.volunteernet.dto.message.MessageResponseWebsocketDto;
import com.volunteernet.volunteernet.dto.message.SaveMessageDto;
import com.volunteernet.volunteernet.exceptions.ChatNotExistsInUserChatsException;
import com.volunteernet.volunteernet.models.Chat;
import com.volunteernet.volunteernet.models.ChatNotification;
import com.volunteernet.volunteernet.models.Message;
import com.volunteernet.volunteernet.models.User;
import com.volunteernet.volunteernet.models.UserChat;
import com.volunteernet.volunteernet.repositories.IChatNotificationRepository;
import com.volunteernet.volunteernet.repositories.IChatRepository;
import com.volunteernet.volunteernet.repositories.IMessageRepository;
import com.volunteernet.volunteernet.repositories.IUserRepository;
import com.volunteernet.volunteernet.services.IServices.IMessageService;
import com.volunteernet.volunteernet.services.IServices.INotificationCountService;
import com.volunteernet.volunteernet.util.handler.memory.ChatUserPresenceTracker;
import com.volunteernet.volunteernet.util.handler.memory.UserPresenceTracker;

@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private IChatRepository chatRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IMessageRepository messageRepository;

    @Autowired
    private IChatNotificationRepository chatNotificationRepository;

    @Autowired
    private INotificationCountService notificationCountService;

    @Autowired
    private ChatUserPresenceTracker chatUserPresenceTracker;

    @Autowired
    private UserPresenceTracker userPresenceTracker;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public List<MessageResponseDto> findAllMessagesByChat(int chatId) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new ChatNotExistsInUserChatsException());
        verifyExistsChatInUserChats(chat.getId());

        User user = userRepository.findByUsername(getUserAutheticated()).get();

        return chat.getMessages()
                .stream()
                .map(message -> new MessageResponseDto(message.getId(), message.getMessage(),
                        message.getUser().getUsername(), (user.getId() == message.getUser().getId())))
                .collect(Collectors.toList());
    }

    @Override
    public MessageResponseDto saveMessage(int chatId, SaveMessageDto saveMessageDto) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new ChatNotExistsInUserChatsException());
        verifyExistsChatInUserChats(chat.getId());

        User user = userRepository.findByUsername(getUserAutheticated()).get();
        Message newMessage = new Message(saveMessageDto.getMessage(), user, chat);

        messageRepository.save(newMessage);

        MessageResponseDto messageResponseDto = new MessageResponseDto(newMessage.getId(), newMessage.getMessage(),
                newMessage.getUser().getUsername(), true);

        MessageResponseWebsocketDto messageResponseWebsocketDto = new MessageResponseWebsocketDto();
        messageResponseWebsocketDto.setMessageResponseDto(messageResponseDto);
        messageResponseWebsocketDto.setChatId(chat.getId());

        List<UserChat> usersChats = chat.getUsers().stream().filter(userChat -> userChat.getUser().getId() != user.getId())
                .collect(Collectors.toList());

        for (UserChat userChat : usersChats) {
            if (!chatUserPresenceTracker.isUserConnectedToChat(chat.getId(), userChat.getUser())) {
                ChatNotification chatNotification = chatNotificationRepository.findByUserIdAndChatId(userChat.getUser().getId(),
                        chat.getId());

                if (chatNotification == null) {
                    chatNotification = new ChatNotification();
                    chatNotification.setUser(userChat.getUser());
                    chatNotification.setChat(chat);
                    chatNotification.setUnreadCount(1);
                } else {
                    chatNotification.setUnreadCount(chatNotification.getUnreadCount() + 1);
                }

                chatNotificationRepository.save(chatNotification);
            }

            if (userPresenceTracker.isUserOnline(userChat.getUser().getId())) {
                messageResponseWebsocketDto.getMessageResponseDto().setMyMessage(false);
                messagingTemplate.convertAndSendToUser(String.valueOf(userChat.getUser().getId()),
                        "/queue/notifications/chats/all", messageResponseWebsocketDto);

                messagingTemplate.convertAndSendToUser(String.valueOf(userChat.getUser().getId()),
                        "/queue/notifications/chats", "ok");
            } else {
                notificationCountService.incrementNotificationChatCountByUser(userChat.getUser().getId());
            }
        }

        messageResponseDto.setMyMessage(true);
        return messageResponseDto;
    }

    private String getUserAutheticated() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    private void verifyExistsChatInUserChats(Integer chatId) {
        User user = userRepository.findByUsername(getUserAutheticated()).get();

        boolean existsChatInUserChats = user.getChats().stream().anyMatch(userChat -> userChat.getChat().getId() == chatId);

        if (!existsChatInUserChats) {
            throw new ChatNotExistsInUserChatsException();
        }
    }
}
