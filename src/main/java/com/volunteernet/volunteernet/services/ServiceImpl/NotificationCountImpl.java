package com.volunteernet.volunteernet.services.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.volunteernet.volunteernet.models.NotificationCount;
import com.volunteernet.volunteernet.models.User;
import com.volunteernet.volunteernet.repositories.INotificationCountRepository;
import com.volunteernet.volunteernet.repositories.IUserRepository;
import com.volunteernet.volunteernet.services.IServices.INotificationCountService;

@Service
public class NotificationCountImpl implements INotificationCountService {

    @Autowired
    private INotificationCountRepository notificationCountRepository;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public int getGeneralCount() {
        User user = userRepository.findByUsername(getUserAutheticated()).get();
        NotificationCount notificationCount = notificationCountRepository.findByUserId(user.getId());
        return notificationCount != null ? notificationCount.getGeneralCount() : 0;
    }

    @Override
    public int getChatCount() {
        User user = userRepository.findByUsername(getUserAutheticated()).get();
        NotificationCount notificationCount = notificationCountRepository.findByUserId(user.getId());
        return notificationCount != null ? notificationCount.getChatCount() : 0;
    }

    @Override
    public void resetGeneralCount() {
        User user = userRepository.findByUsername(getUserAutheticated()).get();
        NotificationCount notificationCount = notificationCountRepository.findByUserId(user.getId());
        if (notificationCount != null) {
            notificationCount.setGeneralCount(0);
            notificationCountRepository.save(notificationCount);
        }
    }

    @Override
    public void resetChatCount() {
        User user = userRepository.findByUsername(getUserAutheticated()).get();
        NotificationCount notificationCount = notificationCountRepository.findByUserId(user.getId());
        if (notificationCount != null) {
            notificationCount.setChatCount(0);
            notificationCountRepository.save(notificationCount);
        }
    }

    @Override
    public void incrementGeneralCount(int userId) {
        NotificationCount notificationCount = notificationCountRepository.findByUserId(userId);

        if (notificationCount == null) {
            notificationCount = new NotificationCount(userId, 1, 0);
        } else {
            notificationCount.setGeneralCount(notificationCount.getGeneralCount() + 1);
        }

        notificationCountRepository.save(notificationCount);
    }

    @Override
    public void incrementChatCount() {
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
    public void incrementChatCountByUser(int userId) {
        NotificationCount notificationCount = notificationCountRepository.findByUserId(userId);

        if (notificationCount == null) {
            notificationCount = new NotificationCount(userId, 0, 1);
        } else {
            notificationCount.setChatCount(notificationCount.getChatCount() + 1);
        }

        notificationCountRepository.save(notificationCount);
    }

    private String getUserAutheticated() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
