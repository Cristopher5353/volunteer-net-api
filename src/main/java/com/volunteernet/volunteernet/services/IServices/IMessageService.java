package com.volunteernet.volunteernet.services.IServices;

import java.util.List;
import com.volunteernet.volunteernet.dto.message.MessageResponseDto;
import com.volunteernet.volunteernet.dto.message.SaveMessageDto;

public interface IMessageService {
    List<MessageResponseDto> findAllByChat(int chatId);

    MessageResponseDto save(int chatId, SaveMessageDto saveMessageDto);
}
