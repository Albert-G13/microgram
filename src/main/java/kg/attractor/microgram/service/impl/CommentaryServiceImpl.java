package kg.attractor.microgram.service.impl;

import jakarta.transaction.Transactional;
import kg.attractor.microgram.dto.CommentaryDto;
import kg.attractor.microgram.exception.PublicationNotFoundException;
import kg.attractor.microgram.exception.UserNotFoundException;
import kg.attractor.microgram.model.Commentary;
import kg.attractor.microgram.model.Publication;
import kg.attractor.microgram.model.User;
import kg.attractor.microgram.repository.CommentaryRepository;
import kg.attractor.microgram.repository.PublicationRepository;
import kg.attractor.microgram.repository.UserRepository;
import kg.attractor.microgram.service.CommentaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentaryServiceImpl implements CommentaryService {
    private final CommentaryRepository commentaryRepository;
    private final PublicationRepository publicationRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public CommentaryDto addComment(CommentaryDto dto, String userEmail) {
        log.info("Попытка добавления комментария от пользователя: {}", userEmail);
        User author = userRepository.findByEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);

        Publication publication = publicationRepository.findById(dto.getPublicationId())
                .orElseThrow(PublicationNotFoundException::new);

        Commentary commentary = Commentary.builder()
                .content(dto.getContent())
                .dateOfPublication(LocalDateTime.now())
                .user(author)
                .publication(publication)
                .build();

        commentaryRepository.save(commentary);
        log.info("Комментарий успешно сохранен. ID комментария: {}", commentary.getId());

        publication.setCommentaryCount(publication.getCommentaryCount() + 1);
        publicationRepository.save(publication);
        log.info("Счетчик комментариев для публикации ID {} обновлен", publication.getId());

        return CommentaryDto.builder()
                .content(commentary.getContent())
                .dateOfPublication(commentary.getDateOfPublication())
                .authorId(author.getId())
                .publicationId(publication.getId())
                .build();
    }

    @Override
    public List<CommentaryDto> getCommentsByPublicationId(Long pubId) {
        log.info("Запрос списка комментариев для публикации ID: {}", pubId);
        return publicationRepository.findById(pubId)
                .map(pub -> pub.getComments().stream()
                        .map(c -> CommentaryDto.builder()
                                .id(c.getId())
                                .content(c.getContent())
                                .dateOfPublication(c.getDateOfPublication())
                                .authorId(c.getUser().getId())
                                .authorLogin(c.getUser().getLogin())
                                .build())
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }
    @Override
    public boolean deleteComment(Long commentId, String userEmail) {
        log.info("Запрос на удаление комментария ID: {} пользователем: {}", commentId, userEmail);
        Optional<Commentary> commentOpt = commentaryRepository.findById(commentId);

        if (commentOpt.isPresent()) {
            Commentary comment = commentOpt.get();
            if (comment.getUser().getEmail().equals(userEmail)) {
                commentaryRepository.delete(comment);
                log.info("Комментарий ID: {} успешно удален", commentId);
                return true;
            }
        }
        return false;
    }
}
