package com.volunteernet.volunteernet.services.IServices;

import java.util.List;
import com.volunteernet.volunteernet.dto.chat.ChatResponseDto;

public interface IChatService {
    List<ChatResponseDto> findChatsByUser();
}
