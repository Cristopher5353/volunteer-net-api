package com.volunteernet.volunteernet.dto.chatMember;

public class ChatMemberResponseDto {
    private int id;
    private String user;

    public ChatMemberResponseDto(int id, String user) {
        this.id = id;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
