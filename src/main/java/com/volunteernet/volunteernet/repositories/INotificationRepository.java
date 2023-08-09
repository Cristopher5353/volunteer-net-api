package com.volunteernet.volunteernet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.volunteernet.volunteernet.models.Notification;

public interface INotificationRepository extends JpaRepository<Notification, Integer>{
    
}
