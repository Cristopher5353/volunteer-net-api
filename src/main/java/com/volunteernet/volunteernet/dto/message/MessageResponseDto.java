package com.volunteernet.volunteernet.dto.message;

public class MessageResponseDto {
    private int id;
    private String message;
    private String user;

    public MessageResponseDto(int id, String message, String user) {
        this.id = id;
        this.message = message;
        this.user = user;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
