package com.volunteernet.volunteernet.controllers;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<Object> getAll(@PageableDefault(page = 0, size = 5) Pageable pageable) {
        List<PublicationResponseDto> publications = publicationService.findAll(pageable);
        return ResponseHandlerOk.generateResponse("Publicaciones", HttpStatus.OK, publications);
    }

    @GetMapping("/api/users/{userId}/publications")
    public ResponseEntity<Object> getAllByUser(@PathVariable Integer userId,
            @PageableDefault(page = 0, size = 5) Pageable pageable) {
        List<PublicationResponseDto> publications = publicationService.findAllByUserId(userId, pageable);
        return ResponseHandlerOk.generateResponse("Publicaciones", HttpStatus.OK, publications);
    }

    @PostMapping("/api/publications")
    public ResponseEntity<Object> save(@Valid @RequestPart PublicationSaveDto publicationSaveDto,
            @RequestPart(required = false) MultipartFile[] images) throws IOException {
        PublicationResponseDto newPublication = publicationService.save(publicationSaveDto, images);
        return ResponseHandlerOk.generateResponse("Publicación registrada correctamente", HttpStatus.CREATED,
                newPublication);
    }
}
