package com.volunteernet.volunteernet.services.IServices;

import com.volunteernet.volunteernet.dto.user.UserResponseDto;
import com.volunteernet.volunteernet.dto.user.UserSaveDto;

public interface IUserService {
    void saveUser(UserSaveDto userSaveDto);
    UserResponseDto findUserById(int id);
    void userFollow(int id);
    void userUnFollow(int id);
    void userRequestToJoinGroup(int userId);
    void connectUser();
    void disconnectUser();
}
