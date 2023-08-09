package com.volunteernet.volunteernet.dto.chatNotification;

public class ChatNotificationDto {
    private int chatId;
    private String username;
    private int unreadCount;

    public ChatNotificationDto() {
    }

    public ChatNotificationDto(int chatId, String username, int unreadCount) {
        this.chatId = chatId;
        this.username = username;
        this.unreadCount = unreadCount;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }
}
