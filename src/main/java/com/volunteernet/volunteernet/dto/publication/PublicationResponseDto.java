package com.volunteernet.volunteernet.dto.publication;

public class PublicationResponseDto {
    private int id;
    private String description;
    private String user;
    private int userId;
    private String createdAt;

    public PublicationResponseDto() {
    }

    public PublicationResponseDto(int id, String description, String user, int userId, String createdAt) {
        this.id = id;
        this.description = description;
        this.user = user;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}