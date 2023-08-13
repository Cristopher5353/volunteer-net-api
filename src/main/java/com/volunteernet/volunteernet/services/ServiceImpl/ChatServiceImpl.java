package com.volunteernet.volunteernet.services.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.volunteernet.volunteernet.dto.chatNotification.ChatNotificationDto;
import com.volunteernet.volunteernet.models.ChatNotification;
import com.volunteernet.volunteernet.models.User;
import com.volunteernet.volunteernet.repositories.IChatNotificationRepository;
import com.volunteernet.volunteernet.repositories.IUserRepository;
import com.volunteernet.volunteernet.services.IServices.IChatService;
import com.volunteernet.volunteernet.util.handler.memory.ChatUserPresenceTracker;

@Service
public class ChatServiceImpl implements IChatService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IChatNotificationRepository chatNotificationRepository;

    @Autowired
    private ChatUserPresenceTracker chatUserPresenceTracker;

    @Override
    public List<ChatNotificationDto> findAllByUser() {
        User user = userRepository.findByUsername(getUserAutheticated()).get();
        List<ChatNotificationDto> chatsByUser = new ArrayList<>();

        user.getChats().stream().filter(userChat -> userChat.getState() == 1).forEach(userChat -> {
            ChatNotification chatNotification = chatNotificationRepository.findByUserIdAndChatId(user.getId(),
                    userChat.getChat().getId());
            ChatNotificationDto chatNotificationDto = new ChatNotificationDto(userChat.getChat().getId(),
                    userChat.getChat().getUser().getUsername(),
                    (chatNotification == null) ? 0 : chatNotification.getUnreadCount());
            chatsByUser.add(chatNotificationDto);
        });

        return chatsByUser;
    }

    @Override
    public void disconnectUser(int chatId) {
        User user = userRepository.findByUsername(getUserAutheticated()).get();
        chatUserPresenceTracker.userDisconnectedFromChat(chatId, user);
    }

    private String getUserAutheticated() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
