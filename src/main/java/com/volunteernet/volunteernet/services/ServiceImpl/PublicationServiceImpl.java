package com.volunteernet.volunteernet.services.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.volunteernet.volunteernet.dto.publication.PublicationResponseDto;
import com.volunteernet.volunteernet.dto.publication.PublicationSaveDto;
import com.volunteernet.volunteernet.models.Publication;
import com.volunteernet.volunteernet.models.User;
import com.volunteernet.volunteernet.repositories.IPublicationRepository;
import com.volunteernet.volunteernet.repositories.IUserRepository;
import com.volunteernet.volunteernet.services.IServices.IPublicationService;

@Service
public class PublicationServiceImpl implements IPublicationService {

    @Autowired
    private IPublicationRepository publicationRepository;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public void savePublication(PublicationSaveDto publicationSaveDto) {
        Publication newPublication = new Publication();
        newPublication.setDescription(publicationSaveDto.getDescription());
        newPublication.setUser(userRepository.findByUsername(getUserAutheticated()).get());

        publicationRepository.save(newPublication);
    }

    @Override
    public List<PublicationResponseDto> findAllPublications() {
        User user = userRepository.findByUsername(getUserAutheticated()).get();
        
        return publicationRepository.findByUserIdNotEqual(user.getId())
                .stream()
                .map(publication -> new PublicationResponseDto(publication.getId(), publication.getDescription(),
                        publication.getUser().getUsername()))
                .collect(Collectors.toList());
    }

    private String getUserAutheticated() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
