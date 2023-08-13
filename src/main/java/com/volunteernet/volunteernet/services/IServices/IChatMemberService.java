package com.volunteernet.volunteernet.services.IServices;

import java.util.List;
import com.volunteernet.volunteernet.dto.chatMember.ChatMemberResponseDto;

public interface IChatMemberService {
    List<ChatMemberResponseDto> getAllRequestsByUser();

    void userRequestToJoin(int userId);

    void confirmUserJoin(int userChatId);
}
