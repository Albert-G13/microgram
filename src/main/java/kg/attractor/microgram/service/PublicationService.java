package kg.attractor.microgram.service;

import kg.attractor.microgram.dto.PublicationCreateDto;
import kg.attractor.microgram.dto.PublicationDto;

import java.util.List;

public interface PublicationService {
    void createPublication(PublicationCreateDto dto, String userEmail);

    List<PublicationDto> getFeed(String email);
}
