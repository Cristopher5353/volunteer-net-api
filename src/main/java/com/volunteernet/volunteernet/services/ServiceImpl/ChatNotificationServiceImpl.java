package com.volunteernet.volunteernet.services.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.volunteernet.volunteernet.dto.chatNotification.ChatNotificationResetUnReadCountDto;
import com.volunteernet.volunteernet.models.ChatNotification;
import com.volunteernet.volunteernet.models.User;
import com.volunteernet.volunteernet.repositories.IChatNotificationRepository;
import com.volunteernet.volunteernet.repositories.IUserRepository;
import com.volunteernet.volunteernet.services.IServices.IChatNotificationService;
import com.volunteernet.volunteernet.util.handler.memory.ChatUserPresenceTracker;

@Service
public class ChatNotificationServiceImpl implements IChatNotificationService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IChatNotificationRepository chatNotificationRepository;

    @Autowired
    private ChatUserPresenceTracker chatUserPresenceTracker;

    @Override
    public void resetUnreadCount(
            ChatNotificationResetUnReadCountDto chatNotificationResetUnReadCountDto) {
        User user = userRepository.findByUsername(getUserAutheticated()).get();
        ChatNotification chatNotification = chatNotificationRepository
                .findByUserIdAndChatId(user.getId(), chatNotificationResetUnReadCountDto.getChatId());

        if(chatNotification != null) {
            chatNotification.setUnreadCount(0);
            chatNotificationRepository.save(chatNotification);
        }
                
        chatUserPresenceTracker.userConnectedToChat(chatNotificationResetUnReadCountDto.getChatId(), user);
    }

    private String getUserAutheticated() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
