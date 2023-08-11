package com.volunteernet.volunteernet.services.IServices;

import com.volunteernet.volunteernet.dto.chatNotification.ChatNotificationResetUnReadCountDto;

public interface INotificationCountService {
    void incrementNotificationCount(int userId);
    void incrementNotificationChatCount();
    void incrementNotificationChatCountByUser(int userId);
    int getNotificationCount();
    int getNotificationChatCount();
    void resetNotificationCount();
    void resetNotificationChatCount();
    void resetChatNotification(ChatNotificationResetUnReadCountDto chatNotificationResetDto);
}
