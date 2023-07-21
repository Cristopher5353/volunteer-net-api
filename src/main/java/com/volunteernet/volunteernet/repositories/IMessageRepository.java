package com.volunteernet.volunteernet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.volunteernet.volunteernet.models.Message;

public interface IMessageRepository extends JpaRepository<Message, Integer>{
    
}
