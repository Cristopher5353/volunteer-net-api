package com.volunteernet.volunteernet.services.ServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.volunteernet.volunteernet.dto.publication.PublicationResponseDto;
import com.volunteernet.volunteernet.dto.publication.PublicationSaveDto;
import com.volunteernet.volunteernet.models.Follower;
import com.volunteernet.volunteernet.models.Notification;
import com.volunteernet.volunteernet.models.Publication;
import com.volunteernet.volunteernet.models.User;
import com.volunteernet.volunteernet.repositories.IFollowerRepository;
import com.volunteernet.volunteernet.repositories.INotificationRepository;
import com.volunteernet.volunteernet.repositories.IPublicationRepository;
import com.volunteernet.volunteernet.repositories.IUserRepository;
import com.volunteernet.volunteernet.services.IServices.INotificationCountService;
import com.volunteernet.volunteernet.services.IServices.IPublicationService;
import com.volunteernet.volunteernet.util.handler.memory.UserPresenceTracker;

@Service
public class PublicationServiceImpl implements IPublicationService {

    @Autowired
    private IPublicationRepository publicationRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IFollowerRepository followerRepository;

    @Autowired
    private INotificationRepository notificationRepository;

    @Autowired
    private UserPresenceTracker userPresenceTracker;

    @Autowired
    private INotificationCountService notificationCountService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void save(PublicationSaveDto publicationSaveDto) {
        Publication newPublication = new Publication();
        newPublication.setDescription(publicationSaveDto.getDescription());
        newPublication.setUser(userRepository.findByUsername(getUserAutheticated()).get());
        newPublication.setCreatedAt(getCurrentDateTime());

        publicationRepository.save(newPublication);
        notifyUserFollowers(newPublication);
    }

    @Override
    public List<PublicationResponseDto> findAll() {
        User user = userRepository.findByUsername(getUserAutheticated()).get();

        return publicationRepository.findByUserIdNotEqual(user.getId())
                .stream()
                .map(publication -> new PublicationResponseDto(publication.getId(), publication.getDescription(),
                        publication.getUser().getUsername(), publication.getUser().getId(), publication.getCreatedAt()))
                .collect(Collectors.toList());
    }

    private void notifyUserFollowers(Publication newPublication) {
        User user = userRepository.findByUsername(getUserAutheticated()).get();
        List<Follower> followers = followerRepository.findByFollowingId(user.getId());

        followers.stream().forEach(follower -> {
            if (userPresenceTracker.isUserOnline(follower.getFollower().getId())) {
                messagingTemplate.convertAndSendToUser(String.valueOf(follower.getFollower().getId()),
                        "/queue/notifications", "ok");
            }

            Notification newNotification = new Notification();
            newNotification.setFollower(userRepository.findById(follower.getFollower().getId()).get());
            newNotification.setFollowing(userRepository.findByUsername(getUserAutheticated()).get());
            newNotification.setSourceId(newPublication.getId());
            newNotification.setMessage(user.getUsername() + "agregó una publicación");
            newNotification.setType("publication");

            notificationRepository.save(newNotification);

            notificationCountService.incrementGeneralCount(follower.getFollower().getId());
        });
    }

    private String getUserAutheticated() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    private String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

}
