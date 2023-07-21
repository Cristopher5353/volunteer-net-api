package com.volunteernet.volunteernet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.volunteernet.volunteernet.models.Chat;

public interface IChatRepository extends JpaRepository<Chat, Integer>{
    
}
