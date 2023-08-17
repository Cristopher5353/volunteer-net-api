package com.volunteernet.volunteernet.services.IServices;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.volunteernet.volunteernet.dto.publication.PublicationResponseDto;
import com.volunteernet.volunteernet.dto.publication.PublicationSaveDto;

public interface IPublicationService {
    void save(PublicationSaveDto publicationSaveDto, MultipartFile[] images) throws IOException;

    List<PublicationResponseDto> findAll();
}