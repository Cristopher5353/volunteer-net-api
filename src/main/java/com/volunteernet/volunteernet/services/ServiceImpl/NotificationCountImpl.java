package com.volunteernet.volunteernet.services.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.volunteernet.volunteernet.dto.chatNotification.ChatNotificationResetUnReadCountDto;
import com.volunteernet.volunteernet.models.ChatNotification;
import com.volunteernet.volunteernet.models.NotificationCount;
import com.volunteernet.volunteernet.models.User;
import com.volunteernet.volunteernet.repositories.IChatNotificationRepository;
import com.volunteernet.volunteernet.repositories.INotificationCountRepository;
import com.volunteernet.volunteernet.repositories.IUserRepository;
import com.volunteernet.volunteernet.services.IServices.INotificationCountService;
import com.volunteernet.volunteernet.util.handler.memory.ChatUserPresenceTracker;

@Service
public class NotificationCountImpl implements INotificationCountService {

    @Autowired
    private INotificationCountRepository notificationCountRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IChatNotificationRepository chatNotificationRepository;

    @Autowired
    private ChatUserPresenceTracker chatUserPresenceTracker;

    @Override
    public void incrementNotificationCount(int userId) {
        NotificationCount notificationCount = notificationCountRepository.findByUserId(userId);

        if (notificationCount == null) {
            notificationCount = new NotificationCount(userId, 1, 0);
        } else {
            notificationCount.setCount(notificationCount.getCount() + 1);
        }

        notificationCountRepository.save(notificationCount);
    }

    @Override
    public void incrementNotificationChatCount() {
        User user = userRepository.findByUsername(getUserAutheticated()).get();
        NotificationCount notificationCount = notificationCountRepository.findByUserId(user.getId());

        if (notificationCount == null) {
            notificationCount = new NotificationCount(user.getId(), 0, 1);
        } else {
            notificationCount.setChatCount(notificationCount.getChatCount() + 1);
        }

        notificationCountRepository.save(notificationCount);
    }

    @Override
    public void incrementNotificationChatCountByUser(int userId) {
        NotificationCount notificationCount = notificationCountRepository.findByUserId(userId);

        if (notificationCount == null) {
            notificationCount = new NotificationCount(userId, 0, 1);
        } else {
            notificationCount.setChatCount(notificationCount.getChatCount() + 1);
        }

        notificationCountRepository.save(notificationCount);
    }

    @Override
    public int getNotificationCount() {
        User user = userRepository.findByUsername(getUserAutheticated()).get();
        NotificationCount notificationCount = notificationCountRepository.findByUserId(user.getId());
        return notificationCount != null ? notificationCount.getCount() : 0;
    }

    @Override
    public int getNotificationChatCount() {
        User user = userRepository.findByUsername(getUserAutheticated()).get();
        NotificationCount notificationCount = notificationCountRepository.findByUserId(user.getId());
        return notificationCount != null ? notificationCount.getChatCount() : 0;
    }

    @Override
    public void resetNotificationCount() {
        User user = userRepository.findByUsername(getUserAutheticated()).get();
        NotificationCount notificationCount = notificationCountRepository.findByUserId(user.getId());
        if (notificationCount != null) {
            notificationCount.setCount(0);
            notificationCountRepository.save(notificationCount);
        }
    }

    @Override
    public void resetNotificationChatCount() {
        User user = userRepository.findByUsername(getUserAutheticated()).get();
        NotificationCount notificationCount = notificationCountRepository.findByUserId(user.getId());
        if (notificationCount != null) {
            notificationCount.setChatCount(0);
            notificationCountRepository.save(notificationCount);
        }
    }

    @Override
    public void resetChatNotification(ChatNotificationResetUnReadCountDto chatNotificationResetDto) {
        User user = userRepository.findByUsername(getUserAutheticated()).get();
        ChatNotification chatNotification = chatNotificationRepository.findByUserIdAndChatId(chatNotificationResetDto.getChatId(), user.getId());
        chatNotification.setUnreadCount(0);
        chatNotificationRepository.save(chatNotification);

        chatUserPresenceTracker.userConnectedToChat(chatNotificationResetDto.getChatId(), user);
    }

    private String getUserAutheticated() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
