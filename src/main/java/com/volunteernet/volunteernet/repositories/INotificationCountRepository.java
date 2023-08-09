package com.volunteernet.volunteernet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.volunteernet.volunteernet.models.NotificationCount;

public interface INotificationCountRepository extends JpaRepository<NotificationCount, Integer>{
    NotificationCount findByUserId(int userId);
}
