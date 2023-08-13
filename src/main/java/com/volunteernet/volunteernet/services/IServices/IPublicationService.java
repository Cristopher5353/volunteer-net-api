package com.volunteernet.volunteernet.services.IServices;

import java.util.List;
import com.volunteernet.volunteernet.dto.publication.PublicationResponseDto;
import com.volunteernet.volunteernet.dto.publication.PublicationSaveDto;

public interface IPublicationService {
    void save(PublicationSaveDto publicationSaveDto);

    List<PublicationResponseDto> findAll();
}
