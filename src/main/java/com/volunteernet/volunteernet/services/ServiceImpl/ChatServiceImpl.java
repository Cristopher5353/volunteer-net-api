package com.volunteernet.volunteernet.services.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.volunteernet.volunteernet.dto.chat.ChatResponseDto;
import com.volunteernet.volunteernet.models.User;
import com.volunteernet.volunteernet.repositories.IUserRepository;
import com.volunteernet.volunteernet.services.IServices.IChatService;

@Service
public class ChatServiceImpl implements IChatService{
    @Autowired
    private IUserRepository userRepository;

    @Override
    public List<ChatResponseDto> findChatsByUser() {
        User user = userRepository.findByUsername(getUserAutheticated()).get();
        return user.getChats().stream().map(chat -> new ChatResponseDto(chat.getId())).collect(Collectors.toList());
    }

    private String getUserAutheticated() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
