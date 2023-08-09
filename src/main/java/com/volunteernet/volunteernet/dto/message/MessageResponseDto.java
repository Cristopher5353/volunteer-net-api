package com.volunteernet.volunteernet.dto.message;

public class MessageResponseDto {
    private int id;
    private String message;
    private String user;
    private boolean myMessage;

    public MessageResponseDto(int id, String message, String user, boolean myMessage) {
        this.id = id;
        this.message = message;
        this.user = user;
        this.myMessage = myMessage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isMyMessage() {
        return myMessage;
    }

    public void setMyMessage(boolean myMessage) {
        this.myMessage = myMessage;
    }
}
