package com.volunteernet.volunteernet.dto.publication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PublicationSaveDto {
    @NotBlank(message = "El campo que estas pensando no debe estar vacío")
    @Size(max = 500, message = "El campo que estas pensando debe tener como máximo 500 caracteres")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
