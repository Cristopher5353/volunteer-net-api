package com.volunteernet.volunteernet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.volunteernet.volunteernet.models.UserChat;

public interface IUserChatRepository extends JpaRepository<UserChat, Integer>{
    
}
