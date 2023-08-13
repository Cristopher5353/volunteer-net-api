package com.volunteernet.volunteernet.services.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.volunteernet.volunteernet.dto.chatMember.ChatMemberResponseDto;
import com.volunteernet.volunteernet.exceptions.ResourceNotFoundException;
import com.volunteernet.volunteernet.exceptions.UserIsNotChatAdministrator;
import com.volunteernet.volunteernet.exceptions.UserIsNotVolunteerGroupException;
import com.volunteernet.volunteernet.models.Chat;
import com.volunteernet.volunteernet.models.ChatMember;
import com.volunteernet.volunteernet.models.User;
import com.volunteernet.volunteernet.repositories.IChatRepository;
import com.volunteernet.volunteernet.repositories.IChatMemberRepository;
import com.volunteernet.volunteernet.repositories.IUserRepository;
import com.volunteernet.volunteernet.services.IServices.IChatMemberService;

@Service
public class ChatMemberServiceImpl implements IChatMemberService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IChatRepository chatRepository;

    @Autowired
    private IChatMemberRepository chatMemberRepository;

    @Override
    public List<ChatMemberResponseDto> getAllRequestsByUser() {
        User user = userRepository.findByUsername(getUserAutheticated()).get();

        if (user.getRole().getId() != 2) {
            throw new UserIsNotChatAdministrator();
        }

        Chat chat = chatRepository.findByUserId(user.getId());
        List<ChatMember> requestsByChat = chatMemberRepository.findRequestsByChatId(chat.getId());

        return requestsByChat.stream()
                .map(chatMember -> new ChatMemberResponseDto(chatMember.getId(), chatMember.getUser().getId(), chatMember.getUser().getUsername()))
                .collect(Collectors.toList());
    }

    @Override
    public void userRequestToJoin(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException());

        if (user.getRole().getId() != 2) {
            throw new UserIsNotVolunteerGroupException();
        }

        Chat chat = chatRepository.findByUserId(userId);
        User userAuthenticated = userRepository.findByUsername(getUserAutheticated()).get();
        ChatMember chatMember = chatMemberRepository.findByUserIdAndChatId(userAuthenticated.getId(), chat.getId());

        if (chatMember == null) {
            ChatMember newChatMember = new ChatMember();
            newChatMember.setUser(userAuthenticated);
            newChatMember.setChat(chat);
            newChatMember.setRequest(true);
            newChatMember.setState(0);

            chatMemberRepository.save(newChatMember);
        }
    }

    @Override
    public void confirmUserJoin(int userChatId) {
        ChatMember chatMember = chatMemberRepository.findById(userChatId)
                .orElseThrow(() -> new ResourceNotFoundException());
        boolean isChatAdministrator = chatMember.getChat().getUser().getId() == userRepository
                .findByUsername(getUserAutheticated()).get().getId();

        if (!isChatAdministrator) {
            throw new UserIsNotChatAdministrator();
        }

        chatMember.setState(1);
        chatMemberRepository.save(chatMember);
    }

    private String getUserAutheticated() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
