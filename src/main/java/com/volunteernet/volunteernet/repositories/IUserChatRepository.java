package com.volunteernet.volunteernet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.volunteernet.volunteernet.models.UserChat;

public interface IUserChatRepository extends JpaRepository<UserChat, Integer>{
    @Query("SELECT uc FROM UserChat uc WHERE uc.user.id = :userId AND uc.chat.id = :chatId")
    UserChat findByUserIdAndChatId(int userId, int chatId);
}
