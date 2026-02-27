package kg.attractor.microgram.service.impl;

import kg.attractor.microgram.dto.PublicationCreateDto;
import kg.attractor.microgram.dto.PublicationDto;
import kg.attractor.microgram.exception.UserNotFoundException;
import kg.attractor.microgram.model.Follow;
import kg.attractor.microgram.model.Publication;
import kg.attractor.microgram.model.User;
import kg.attractor.microgram.repository.FollowRepository;
import kg.attractor.microgram.repository.PublicationRepository;
import kg.attractor.microgram.repository.UserRepository;
import kg.attractor.microgram.service.FileService;
import kg.attractor.microgram.service.PublicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class PublicationServiceImpl implements PublicationService {
    private final PublicationRepository repository;
    private final FileService fileService;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final PublicationRepository publicationRepository;

    @Override
    public void createPublication(PublicationCreateDto dto, String userEmail) {
        log.info("Начало создания публикации для пользователя: {}", userEmail);
        User author = userRepository.findByEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);

        String fileName = fileService.saveUploadFile(dto.getFile(), "images");
        log.debug("Файл публикации сохранен под именем: {}", fileName);
        Publication publication = Publication.builder()
                .image(fileName)
                .description(dto.getDescription())
                .dateOfPublication(LocalDateTime.now())
                .author(author)
                .likesCount(0L)
                .commentaryCount(0L)
                .build();

        repository.save(publication);
        log.info("Публикация успешно создана. ID: {}, Автор: {}", publication.getId(), userEmail);
    }

    @Override
    public List<PublicationDto> getFeed(String email) {
        log.info("Запрос ленты новостей для пользователя: {}", email);
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        List<User> followings = followRepository.findAllByFollower(currentUser).stream()
                .map(Follow::getFollowing)
                .toList();
        log.debug("Пользователь {} подписан на {} человек", email, followings.size());
        return publicationRepository.findAllByAuthorInOrderByDateOfPublicationDesc(followings)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    private PublicationDto mapToDto(Publication p) {
        return PublicationDto.builder()
                .id(p.getId())
                .image(p.getImage())
                .description(p.getDescription())
                .dateOfPublication(p.getDateOfPublication())
                .likesCount(p.getLikesCount())
                .commentaryCount(p.getCommentaryCount())
                .authorLogin(p.getAuthor().getLogin())
                .authorAvatar(p.getAuthor().getAvatar())
                .build();
    }
}
