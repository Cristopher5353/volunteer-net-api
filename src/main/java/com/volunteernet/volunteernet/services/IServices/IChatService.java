package com.volunteernet.volunteernet.services.IServices;

import java.util.List;
import com.volunteernet.volunteernet.dto.chatNotification.ChatNotificationDto;

public interface IChatService {
    List<ChatNotificationDto> findChatsByUser();
    void disconnectUserFromChat(int chatId);
}
