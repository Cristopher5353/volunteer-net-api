package com.volunteernet.volunteernet.services.IServices;

import com.volunteernet.volunteernet.dto.chatNotification.ChatNotificationResetUnReadCountDto;

public interface IChatNotificationService {
    void resetUnreadCount(ChatNotificationResetUnReadCountDto chatNotificationResetUnReadCountDto);
}