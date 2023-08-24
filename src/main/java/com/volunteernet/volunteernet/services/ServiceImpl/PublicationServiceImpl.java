package com.volunteernet.volunteernet.services.ServiceImpl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.volunteernet.volunteernet.dto.image.ImageResponseDto;
import com.volunteernet.volunteernet.dto.publication.PublicationResponseDto;
import com.volunteernet.volunteernet.dto.publication.PublicationSaveDto;
import com.volunteernet.volunteernet.exceptions.NotImageException;
import com.volunteernet.volunteernet.exceptions.ResourceNotFoundException;
import com.volunteernet.volunteernet.models.Follower;
import com.volunteernet.volunteernet.models.Image;
import com.volunteernet.volunteernet.models.Notification;
import com.volunteernet.volunteernet.models.Publication;
import com.volunteernet.volunteernet.models.User;
import com.volunteernet.volunteernet.repositories.IFollowerRepository;
import com.volunteernet.volunteernet.repositories.IImageRepository;
import com.volunteernet.volunteernet.repositories.INotificationRepository;
import com.volunteernet.volunteernet.repositories.IPublicationRepository;
import com.volunteernet.volunteernet.repositories.IUserRepository;
import com.volunteernet.volunteernet.services.IServices.INotificationCountService;
import com.volunteernet.volunteernet.services.IServices.IPublicationService;
import com.volunteernet.volunteernet.util.handler.cloudinary.CloudinaryService;
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
    private IImageRepository imageRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private UserPresenceTracker userPresenceTracker;

    @Autowired
    private INotificationCountService notificationCountService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public List<PublicationResponseDto> findAll(Pageable pageable) {
        User user = userRepository.findByUsername(getUserAutheticated()).get();

        return publicationRepository.findAllByUserIdNotEqual(user.getId(), pageable)
                .getContent()
                .stream()
                .map(publication -> new PublicationResponseDto(publication.getId(), publication.getDescription(),
                        publication.getUser().getUsername(), publication.getUser().getId(), publication.getCreatedAt(),
                        publication.getImages().stream()
                                .map(image -> new ImageResponseDto(image.getId(), image.getUrl()))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @Override
    public List<PublicationResponseDto> findAllByUserId(int userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException());

        return publicationRepository.findAllByUserId(user.getId(), pageable).stream()
                .map(publication -> new PublicationResponseDto(publication.getId(),
                        publication.getDescription(), user.getUsername(), user.getId(), publication.getCreatedAt(),
                        publication.getImages().stream()
                                .map(image -> new ImageResponseDto(image.getId(), image.getUrl()))
                                .collect(Collectors.toList()))).collect(Collectors.toList());
    }

    @Override
    public PublicationResponseDto save(PublicationSaveDto publicationSaveDto, MultipartFile[] images)
            throws IOException {
        if (images != null) {
            validateImagesFormat(images);
        }

        List<Image> publicationImages = new ArrayList<>();
        Publication newPublication = new Publication();
        newPublication.setDescription(publicationSaveDto.getDescription());
        newPublication.setUser(userRepository.findByUsername(getUserAutheticated()).get());
        newPublication.setCreatedAt(getCurrentDateTime());

        publicationRepository.save(newPublication);

        if (images != null) {
            for (MultipartFile image : images) {
                Map<?, ?> result = cloudinaryService.upload(image);
                Image newImage = new Image();
                newImage.setUrl((String) result.get("url"));
                newImage.setKey((String) result.get("public_id"));
                newImage.setPublication(newPublication);

                imageRepository.save(newImage);
                publicationImages.add(newImage);
            }
        }

        newPublication.setImages(publicationImages);
        notifyUserFollowers(newPublication);

        return new PublicationResponseDto(newPublication.getId(), newPublication.getDescription(),
                newPublication.getUser().getUsername(), newPublication.getUser().getId(), newPublication.getCreatedAt(),
                newPublication.getImages().stream().map(image -> new ImageResponseDto(image.getId(), image.getUrl()))
                        .collect(Collectors.toList()));
    }

    private void notifyUserFollowers(Publication newPublication) {
        User user = userRepository.findByUsername(getUserAutheticated()).get();
        List<Follower> followers = followerRepository.findAllByFollowingId(user.getId());

        followers.stream().forEach(follower -> {
            if (userPresenceTracker.isUserOnline(follower.getFollower().getId())) {
                messagingTemplate.convertAndSendToUser(String.valueOf(follower.getFollower().getId()),
                        "/queue/notifications", "ok");
            }

            Notification newNotification = new Notification();
            newNotification.setFollower(userRepository.findById(follower.getFollower().getId()).get());
            newNotification.setFollowing(userRepository.findByUsername(getUserAutheticated()).get());
            newNotification.setSourceId(newPublication.getId());
            newNotification.setMessage(user.getUsername() + " agregó una publicación");
            newNotification.setType("publication");
            newNotification.setCreatedAt(getCurrentDateTime());

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

    private void validateImagesFormat(MultipartFile[] images) {
        for (MultipartFile image : images) {
            if (!image.getContentType().startsWith("image/")) {
                throw new NotImageException(image.getOriginalFilename());
            }
        }
    }
}