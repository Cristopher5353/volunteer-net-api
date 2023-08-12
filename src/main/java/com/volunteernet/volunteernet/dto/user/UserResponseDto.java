package com.volunteernet.volunteernet.dto.user;

import java.util.List;
import com.volunteernet.volunteernet.dto.publication.PublicationResponseDto;

public class UserResponseDto {
    private int id;
    private String username;
    private String email;
    private String description;
    private String website;
    private String role;
    private boolean isFollower;
    private int isMember;
    private List<PublicationResponseDto> publications;

    public UserResponseDto() {
    }

    public UserResponseDto(int id, String username, String email, String description, String website, String role,
            boolean isFollower, int isMember, List<PublicationResponseDto> publications) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.description = description;
        this.website = website;
        this.role = role;
        this.isFollower = isFollower;
        this.isMember = isMember;
        this.publications = publications;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isFollower() {
        return isFollower;
    }

    public void setFollower(boolean isFollower) {
        this.isFollower = isFollower;
    }

    public int getIsMember() {
        return isMember;
    }

    public void setIsMember(int isMember) {
        this.isMember = isMember;
    }

    public List<PublicationResponseDto> getPublications() {
        return publications;
    }

    public void setPublications(List<PublicationResponseDto> publications) {
        this.publications = publications;
    }

}
