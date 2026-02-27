package kg.attractor.microgram.service.impl;

import jakarta.transaction.Transactional;
import kg.attractor.microgram.exception.PublicationNotFoundException;
import kg.attractor.microgram.exception.UserNotFoundException;
import kg.attractor.microgram.model.Like;
import kg.attractor.microgram.model.Publication;
import kg.attractor.microgram.model.User;
import kg.attractor.microgram.repository.LikeRepository;
import kg.attractor.microgram.repository.PublicationRepository;
import kg.attractor.microgram.repository.UserRepository;
import kg.attractor.microgram.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final UserRepository userRepository;
    private final PublicationRepository publicationRepository;
    private final LikeRepository likeRepository;

    @Transactional
    @Override
    public Long toggleLike(Long publicationId, String userEmail) {
        log.info("Запрос ToggleLike: пользователь {} для публикации ID {}", userEmail, publicationId);
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Publication pub = publicationRepository.findById(publicationId).orElseThrow(PublicationNotFoundException::new);

        Optional<Like> existingLike = likeRepository.findByUserAndPublication(user, pub);

        if (existingLike.isPresent()) {
            log.info("Лайк уже существует. Удаление лайка (дизлайк) для публикации ID {}", publicationId);
            likeRepository.delete(existingLike.get());
            pub.setLikesCount(pub.getLikesCount() - 1);
        } else {
            log.info("Лайк отсутствует. Добавление нового лайка для публикации ID {}", publicationId);
            Like like = Like.builder().user(user).publication(pub).build();
            likeRepository.save(like);
            pub.setLikesCount(pub.getLikesCount() + 1);
        }

        publicationRepository.save(pub);
        log.info("Счетчик лайков обновлен. Текущее количество: {} для публикации ID {}", pub.getLikesCount(), publicationId);
        return pub.getLikesCount();
    }
}
