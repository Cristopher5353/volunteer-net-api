package com.volunteernet.volunteernet.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserSaveDto {
    @NotBlank(message = "El campo nombre de usuario no debe estar vacío")
    @Size(max = 255, message = "El campo nombre de usuario puede tener como máximo 255 caracteres")
    private String username;

    @NotBlank(message = "El campo contraseña no debe estar vacío")
    @Size(min = 8, message = "El campo contraseña debe tener como mínimo 8 caracteres")
    private String password;

    @NotBlank(message = "El campo correo electrónico no debe estar vacío")
    @Email(message = "El campo correo electrónico debe tener un formato correcto")
    private String email;

    @NotBlank(message = "El campo descripción no debe estar vacío")
    @Size(max = 500, message = "El campo descripción debe tener como máximo 500 caracteres")
    private String description;

    private int role;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRole() {
        return this.role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
