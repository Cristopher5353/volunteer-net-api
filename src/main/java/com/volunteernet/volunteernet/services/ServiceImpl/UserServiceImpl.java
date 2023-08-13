package com.volunteernet.volunteernet.services.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.volunteernet.volunteernet.dto.publication.PublicationResponseDto;
import com.volunteernet.volunteernet.dto.user.UserResponseDto;
import com.volunteernet.volunteernet.dto.user.UserSaveDto;
import com.volunteernet.volunteernet.exceptions.EmailAlreadyExistsException;
import com.volunteernet.volunteernet.exceptions.FollowerAlreadyFollowToFollowing;
import com.volunteernet.volunteernet.exceptions.ResourceNotFoundException;
import com.volunteernet.volunteernet.models.Chat;
import com.volunteernet.volunteernet.models.ChatMember;
import com.volunteernet.volunteernet.models.Follower;
import com.volunteernet.volunteernet.models.Role;
import com.volunteernet.volunteernet.models.User;
import com.volunteernet.volunteernet.repositories.IChatRepository;
import com.volunteernet.volunteernet.repositories.IFollowerRepository;
import com.volunteernet.volunteernet.repositories.IPublicationRepository;
import com.volunteernet.volunteernet.repositories.IRoleRepository;
import com.volunteernet.volunteernet.repositories.IChatMemberRepository;
import com.volunteernet.volunteernet.repositories.IUserRepository;
import com.volunteernet.volunteernet.services.IServices.IUserService;
import com.volunteernet.volunteernet.util.handler.memory.UserPresenceTracker;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IFollowerRepository followerRepository;

    @Autowired
    private IPublicationRepository publicationRepository;

    @Autowired
    private IChatRepository chatRepository;

    @Autowired
    private IChatMemberRepository chatMemberRepository;

    @Autowired
    private UserPresenceTracker userPresenceTracker;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void save(UserSaveDto userSaveDto) {
        Optional<User> findUserByEmail = userRepository.findByEmail(userSaveDto.getEmail());
        Optional<Role> findRoleById = roleRepository.findById(userSaveDto.getRole());

        if (findUserByEmail.isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        if (!findRoleById.isPresent()) {
            throw new ResourceNotFoundException();
        }

        User newUser = new User();
        newUser.setUsername(userSaveDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(userSaveDto.getPassword()));
        newUser.setEmail(userSaveDto.getEmail());
        newUser.setDescription(userSaveDto.getDescription());
        newUser.setRole(findRoleById.get());

        userRepository.save(newUser);

        if (findRoleById.get().getId() == 2) {
            Chat newChat = new Chat();
            newChat.setUser(newUser);

            chatRepository.save(newChat);

            ChatMember newChatMember = new ChatMember();
            newChatMember.setUser(newUser);
            newChatMember.setChat(newChat);
            newChatMember.setRequest(false);
            newChatMember.setState(1);

            chatMemberRepository.save(newChatMember);
        }
    }

    @Override
    public UserResponseDto findById(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
        User userAuthenticated = userRepository.findByUsername(getUserAutheticated()).get();
        int isMember = 0;

        List<PublicationResponseDto> publicationsDto = new ArrayList<>();
        Follower follower = followerRepository.findByFollowingIdAndFollowerId(id,
                userRepository.findByUsername(getUserAutheticated()).get().getId());

        if (user.getRole().getId() == 2) {
            Chat chat = chatRepository.findByUserId(user.getId());
            ChatMember chatMember = chatMemberRepository.findByUserIdAndChatId(userAuthenticated.getId(), chat.getId());

            isMember = (chatMember != null) ? (chatMember.getState() == 1) ? 2 : 1 : 0;
        }

        publicationRepository.findByUserId(id).stream()
                .forEach(publication -> publicationsDto.add(new PublicationResponseDto(publication.getId(),
                        publication.getDescription(), user.getUsername(), user.getId(), publication.getCreatedAt())));

        return new UserResponseDto(user.getId(), user.getUsername(), user.getEmail(), user.getDescription(),
                user.getWebsite(), user.getRole().getName(), follower == null ? false : true, isMember,
                publicationsDto);
    }

    @Override
    public void follow(int id) {
        User following = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
        User follower = userRepository.findByUsername(getUserAutheticated()).get();

        Follower follow = followerRepository.findByFollowingIdAndFollowerId(id,
                follower.getId());

        if (follow != null) {
            throw new FollowerAlreadyFollowToFollowing();
        }

        Follower newFollower = new Follower();
        newFollower.setFollowing(following);
        newFollower.setFollower(follower);

        followerRepository.save(newFollower);
    }

    @Override
    public void unFollow(int id) {
        User following = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());

        Follower follower = followerRepository.findByFollowingIdAndFollowerId(following.getId(),
                userRepository.findByUsername(getUserAutheticated()).get().getId());
        followerRepository.delete(follower);
    }

    @Override
    public void connect() {
        User user = userRepository.findByUsername(getUserAutheticated()).get();
        userPresenceTracker.userConnected(user);
    }

    @Override
    public void disconnect() {
        User user = userRepository.findByUsername(getUserAutheticated()).get();
        userPresenceTracker.userDisconnected(user.getId());
    }

    private String getUserAutheticated() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}