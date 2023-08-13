package com.volunteernet.volunteernet.services.IServices;

import com.volunteernet.volunteernet.dto.user.UserResponseDto;
import com.volunteernet.volunteernet.dto.user.UserSaveDto;

public interface IUserService {
    void save(UserSaveDto userSaveDto);

    UserResponseDto findById(int id);

    void follow(int id);

    void unFollow(int id);

    void connect();

    void disconnect();
}
