package com.volunteernet.volunteernet.services.IServices;

import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.volunteernet.volunteernet.dto.publication.PublicationResponseDto;
import com.volunteernet.volunteernet.dto.publication.PublicationSaveDto;

public interface IPublicationService {
    List<PublicationResponseDto> findAll(Pageable pageable);

    List<PublicationResponseDto> findAllByUserId(int userId, Pageable pageable);

    PublicationResponseDto save(PublicationSaveDto publicationSaveDto, MultipartFile[] images) throws IOException;
}