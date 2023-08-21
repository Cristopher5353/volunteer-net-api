package com.volunteernet.volunteernet.dto.notification;

public class NotificationResponseDto {
    private int id;
    private Integer source;
    private String message;
    private String type;

    public NotificationResponseDto(int id, Integer source, String message, String type) {
        this.id = id;
        this.source = source;
        this.message = message;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
