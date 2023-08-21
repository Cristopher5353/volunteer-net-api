package com.volunteernet.volunteernet.services.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.volunteernet.volunteernet.dto.notification.NotificationResponseDto;
import com.volunteernet.volunteernet.models.User;
import com.volunteernet.volunteernet.repositories.IUserRepository;
import com.volunteernet.volunteernet.services.IServices.INotificationService;

@Service
public class NotificationServiceImpl implements INotificationService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public List<NotificationResponseDto> getAllByUser() {
        User user = userRepository.findByUsername(getUserAutheticated()).get();

        return user.getNotifications()
                .stream()
                .map(notification -> new NotificationResponseDto(notification.getId(), notification.getSourceId(),
                        notification.getMessage(), notification.getType()))
                .collect(Collectors.toList());
    }

    private String getUserAutheticated() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

}
