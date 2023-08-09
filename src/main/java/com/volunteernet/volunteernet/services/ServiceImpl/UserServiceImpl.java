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
import com.volunteernet.volunteernet.exceptions.RoleNotExistsException;
import com.volunteernet.volunteernet.exceptions.UserNotExistsException;
import com.volunteernet.volunteernet.models.Follower;
import com.volunteernet.volunteernet.models.Role;
import com.volunteernet.volunteernet.models.User;
import com.volunteernet.volunteernet.repositories.IFollowerRepository;
import com.volunteernet.volunteernet.repositories.IPublicationRepository;
import com.volunteernet.volunteernet.repositories.IRoleRepository;
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
    private UserPresenceTracker userPresenceTracker;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserSaveDto userSaveDto) {
        Optional<User> findUserByEmail = userRepository.findByEmail(userSaveDto.getEmail());
        Optional<Role> findRoleById = roleRepository.findById(userSaveDto.getRole());

        if (findUserByEmail.isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        if (!findRoleById.isPresent()) {
            throw new RoleNotExistsException();
        }

        User newUser = new User();
        newUser.setUsername(userSaveDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(userSaveDto.getPassword()));
        newUser.setEmail(userSaveDto.getEmail());
        newUser.setDescription(userSaveDto.getDescription());
        newUser.setRole(findRoleById.get());

        userRepository.save(newUser);
    }

    @Override
    public UserResponseDto findUserById(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotExistsException());
        
        List<PublicationResponseDto> publicationsDto = new ArrayList<>();
        Follower follower = followerRepository.findByFollowingIdAndFollowerId(id,
                userRepository.findByUsername(getUserAutheticated()).get().getId());

        publicationRepository.findByUserId(id).stream()
                .forEach(publication -> publicationsDto.add(new PublicationResponseDto(publication.getId(),
                        publication.getDescription(), user.getUsername(), user.getId(), publication.getCreatedAt())));

        return new UserResponseDto(user.getId(), user.getUsername(), user.getEmail(), user.getDescription(),
                user.getWebsite(), user.getRole().getName(), follower == null ? false : true, publicationsDto);
    }

    @Override
    public void userFollow(int id) {
        User following = userRepository.findById(id).orElseThrow(() -> new UserNotExistsException());
        User follower = userRepository.findByUsername(getUserAutheticated()).get();

        Follower follow = followerRepository.findByFollowingIdAndFollowerId(id,
                follower.getId());

        if(follow != null) {
            throw new FollowerAlreadyFollowToFollowing();
        }

        Follower newFollower = new Follower();
        newFollower.setFollowing(following);
        newFollower.setFollower(follower);

        followerRepository.save(newFollower);
    }

    @Override
    public void userUnFollow(int id) {
        User following = userRepository.findById(id).orElseThrow(() -> new UserNotExistsException());
        
        Follower follower = followerRepository.findByFollowingIdAndFollowerId(following.getId(),
                userRepository.findByUsername(getUserAutheticated()).get().getId());
        followerRepository.delete(follower);
    }

    @Override
    public void connectUser() {
        User user = userRepository.findByUsername(getUserAutheticated()).get();
        userPresenceTracker.userConnected(user);
    }

    @Override
    public void disconnectUser() {
        User user = userRepository.findByUsername(getUserAutheticated()).get();
        userPresenceTracker.userDisconnected(user.getId());
    }

    private String getUserAutheticated() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
