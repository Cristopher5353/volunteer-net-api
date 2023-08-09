package com.volunteernet.volunteernet.services.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.volunteernet.volunteernet.dto.chatNotification.ChatNotificationDto;
import com.volunteernet.volunteernet.models.User;
import com.volunteernet.volunteernet.repositories.IUserRepository;
import com.volunteernet.volunteernet.services.IServices.IChatService;
import com.volunteernet.volunteernet.util.handler.memory.ChatUserPresenceTracker;

@Service
public class ChatServiceImpl implements IChatService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ChatUserPresenceTracker chatUserPresenceTracker;

    @Override
    public List<ChatNotificationDto> findChatsByUser() {
        User user = userRepository.findByUsername(getUserAutheticated()).get();
        return user.getChatNotifications().stream().map(chat -> new ChatNotificationDto(chat.getChat().getId(),
                chat.getUser().getUsername(), chat.getUnreadCount())).collect(Collectors.toList());
    }

    @Override
    public void disconnectUserFromChat(int chatId) {
        User user = userRepository.findByUsername(getUserAutheticated()).get();
       chatUserPresenceTracker.userDisconnectedFromChat(chatId, user);
    }

    private String getUserAutheticated() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
