package kg.attractor.microgram.service.impl;

import jakarta.transaction.Transactional;
import kg.attractor.microgram.exception.UserNotFoundException;
import kg.attractor.microgram.model.Follow;
import kg.attractor.microgram.model.User;
import kg.attractor.microgram.repository.FollowRepository;
import kg.attractor.microgram.repository.UserRepository;
import kg.attractor.microgram.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void follow(Long followerId, Long followingId) {
        log.info("Запрос на подписку: Пользователь ID {} хочет подписаться на Пользователя ID {}", followerId, followingId);
        User follower = userRepository.findById(followerId).orElseThrow(UserNotFoundException::new);
        User following = userRepository.findById(followingId).orElseThrow(UserNotFoundException::new);

        Follow follow = Follow.builder()
                .follower(follower)
                .following(following)
                .build();
        followRepository.save(follow);
        log.info("Запись о подписке успешно сохранена в БД");

        follower.setFollowingCount(follower.getFollowingCount() + 1);
        following.setFollowerCount(following.getFollowerCount() + 1);

        userRepository.save(follower);
        userRepository.save(following);
        log.info("Счетчики обновлены: Пользователь {} (following: {}), Пользователь {} (followers: {})",
                follower.getEmail(), follower.getFollowingCount(),
                following.getEmail(), following.getFollowerCount());
    }
}
