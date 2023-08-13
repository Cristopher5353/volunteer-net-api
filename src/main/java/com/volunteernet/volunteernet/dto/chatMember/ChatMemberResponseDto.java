package com.volunteernet.volunteernet.dto.chatMember;

public class ChatMemberResponseDto {
    private int id;
    private int userId;
    private String user;

    public ChatMemberResponseDto(int id, int userId, String user) {
        this.id = id;
        this.userId = userId;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    
}
