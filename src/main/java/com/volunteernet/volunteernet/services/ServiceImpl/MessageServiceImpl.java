package com.volunteernet.volunteernet.services.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.volunteernet.volunteernet.dto.message.MessageResponseDto;
import com.volunteernet.volunteernet.dto.message.SaveMessageDto;
import com.volunteernet.volunteernet.exceptions.ChatNotExistsInUserChatsException;
import com.volunteernet.volunteernet.models.Chat;
import com.volunteernet.volunteernet.models.Message;
import com.volunteernet.volunteernet.models.User;
import com.volunteernet.volunteernet.repositories.IChatRepository;
import com.volunteernet.volunteernet.repositories.IMessageRepository;
import com.volunteernet.volunteernet.repositories.IUserRepository;
import com.volunteernet.volunteernet.services.IServices.IMessageService;

@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private IChatRepository chatRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IMessageRepository messageRepository;

    @Override
    public List<MessageResponseDto> findAllMessagesByChat(Integer chatId) {
        verifyExistsChatInUserChats(chatId);

        Chat chat = chatRepository.findById(chatId).get();

        return chat.getMessages()
                .stream()
                .map(message -> new MessageResponseDto(message.getId(), message.getMessage(),
                        message.getUser().getUsername()))
                .collect(Collectors.toList());
    }

    @Override
    public MessageResponseDto saveMessage(Integer chatId, SaveMessageDto saveMessageDto) {
        verifyExistsChatInUserChats(chatId);

        User user = userRepository.findByUsername(getUserAutheticated()).get();
        Chat chat = chatRepository.findById(chatId).get();
        Message newMessage = new Message(saveMessageDto.getMessage(), user, chat);

        messageRepository.save(newMessage);
        MessageResponseDto messageResponseDto = new MessageResponseDto(newMessage.getId(), newMessage.getMessage(),
                newMessage.getUser().getUsername());

        return messageResponseDto;

        // generate wesocket
    }

    /*
     * @Override
     * public void saveMessage(Integer chatId, SaveMessageDto saveMessageDto) {
     * verifyExistsChatInUserChats(chatId);
     * 
     * User user = userRepository.findByUsername(getUserAutheticated()).get();
     * Chat chat = chatRepository.findById(chatId).get();
     * Message newMessage = new Message(saveMessageDto.getMessage(), user, chat);
     * 
     * messageRepository.save(newMessage);
     * 
     * //generate wesocket
     * }
     */

    private String getUserAutheticated() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    private void verifyExistsChatInUserChats(Integer chatId) {
        User user = userRepository.findByUsername(getUserAutheticated()).get();

        boolean existsChatInUserChats = user.getChats().stream().anyMatch(chat -> chat.getId() == chatId);

        if (!existsChatInUserChats) {
            throw new ChatNotExistsInUserChatsException();
        }
    }

}
