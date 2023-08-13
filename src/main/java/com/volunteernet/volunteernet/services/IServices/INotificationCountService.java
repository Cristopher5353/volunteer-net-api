package com.volunteernet.volunteernet.services.IServices;

public interface INotificationCountService {
    int getGeneralCount();

    int getChatCount();

    void resetGeneralCount();

    void resetChatCount();

    void incrementGeneralCount(int userId);

    void incrementChatCount();

    void incrementChatCountByUser(int userId);
}