package com.volunteernet.volunteernet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.volunteernet.volunteernet.models.ChatMember;

public interface IChatMemberRepository extends JpaRepository<ChatMember, Integer>{
    @Query("SELECT cm FROM ChatMember cm WHERE cm.user.id = :userId AND cm.chat.id = :chatId")
    ChatMember findByUserIdAndChatId(int userId, int chatId);

    @Query("SELECT cm FROM ChatMember cm WHERE cm.chat.id = :chatId AND cm.isRequest = true AND cm.state = 0")
    List<ChatMember> findRequestsByChatId(int chatId);
}