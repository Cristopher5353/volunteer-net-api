package com.volunteernet.volunteernet.controllers;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.volunteernet.volunteernet.dto.publication.PublicationResponseDto;
import com.volunteernet.volunteernet.dto.publication.PublicationSaveDto;
import com.volunteernet.volunteernet.services.IServices.IPublicationService;
import com.volunteernet.volunteernet.util.handler.ok.ResponseHandlerOk;
import jakarta.validation.Valid;

@RestController
public class PublicationController {

    @Autowired
    private IPublicationService publicationService;

    @GetMapping("/api/publications")
    public ResponseEntity<Object> getAll() {
        List<PublicationResponseDto> publications = publicationService.findAll();
        return ResponseHandlerOk.generateResponse("Publicaciones", HttpStatus.OK, publications);
    }

    @PostMapping("/api/publications")
    public ResponseEntity<Object> save(@Valid @RequestPart PublicationSaveDto publicationSaveDto, @RequestPart(required = false) MultipartFile[] images) throws IOException {
        PublicationResponseDto newPublication = publicationService.save(publicationSaveDto, images);
        return ResponseHandlerOk.generateResponse("Publicación registrada correctamente", HttpStatus.CREATED, newPublication);
    }
}
