package com.volunteernet.volunteernet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.volunteernet.volunteernet.models.ChatNotification;

public interface IChatNotificationRepository extends JpaRepository<ChatNotification, Integer> {
    @Query("SELECT c FROM ChatNotification c WHERE c.user.id = :userId AND c.chat.id = :chatId")
    ChatNotification findByUserIdAndChatId(int userId, int chatId);
}
