package com.volunteernet.volunteernet.dto.publication;

import java.util.List;
import com.volunteernet.volunteernet.dto.image.ImageResponseDto;

public class PublicationResponseDto {
    private int id;
    private String description;
    private String user;
    private int userId;
    private String createdAt;
    private List<ImageResponseDto> images;

    public PublicationResponseDto() {
    }

    public PublicationResponseDto(int id, String description, String user, int userId, String createdAt,
            List<ImageResponseDto> images) {
        this.id = id;
        this.description = description;
        this.user = user;
        this.userId = userId;
        this.createdAt = createdAt;
        this.images = images;
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

    public List<ImageResponseDto> getImages() {
        return images;
    }

    public void setImages(List<ImageResponseDto> images) {
        this.images = images;
    }
}