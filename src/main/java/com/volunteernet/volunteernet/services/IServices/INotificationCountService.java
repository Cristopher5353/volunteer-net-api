package com.volunteernet.volunteernet.services.IServices;

import com.volunteernet.volunteernet.dto.chatNotification.ChatNotificationResetUnReadCountDto;

public interface INotificationCountService {
    void incrementNotificationCount(int userId);
    void incrementNotificationChatCount();
    int getNotificationCount();
    int getNotificationChatCount();
    void resetNotificationCount();
    void resetNotificationChatCount();
    void resetChatNotification(ChatNotificationResetUnReadCountDto chatNotificationResetDto);
}
