package com.volunteernet.volunteernet.services.IServices;

import java.util.List;
import com.volunteernet.volunteernet.dto.notification.NotificationResponseDto;

public interface INotificationService {
    List<NotificationResponseDto> getAllByUser(); 
}
