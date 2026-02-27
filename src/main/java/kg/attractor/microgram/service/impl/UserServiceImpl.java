package kg.attractor.microgram.service.impl;
import kg.attractor.microgram.dto.UserDto;
import kg.attractor.microgram.dto.UserEditDto;
import kg.attractor.microgram.dto.UserProfileDto;
import kg.attractor.microgram.dto.UserRegisterDto;
import kg.attractor.microgram.exception.RoleNotFoundException;
import kg.attractor.microgram.exception.UserNotAuthorizedException;
import kg.attractor.microgram.exception.UserNotFoundException;
import kg.attractor.microgram.model.Follow;
import kg.attractor.microgram.model.Role;
import kg.attractor.microgram.model.User;
import kg.attractor.microgram.repository.FollowRepository;
import kg.attractor.microgram.repository.PublicationRepository;
import kg.attractor.microgram.repository.RoleRepository;
import kg.attractor.microgram.repository.UserRepository;
import kg.attractor.microgram.service.FileService;
import kg.attractor.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final PublicationRepository publicationRepository;
    private final FollowRepository followRepository;
    private final FileService fileService;

    @Override
    public void register(UserRegisterDto dto) {
        log.info("Начало регистрации нового пользователя с логином: {}", dto.getLogin());
        Role userRole = roleRepository.findByRole("USER")
                .orElseThrow(RoleNotFoundException::new);

        User user = User.builder()
                .login(dto.getLogin())
                .email(dto.getEmail())
                .name(dto.getName())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(userRole)
                .enabled(true)
                .followerCount(0L)
                .followingCount(0L)
                .build();

        userRepository.save(user);
        log.info("Пользователь {} успешно зарегистрирован. ID: {}", user.getLogin(), user.getId());
    }

    @Override
    public List<UserDto> searchUsers(String query) {
        log.debug("Выполняется поиск пользователей по запросу: {}", query);
        return userRepository.findAllByLoginContainingIgnoreCaseOrNameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query, query)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public UserProfileDto getProfile(String login) {
        log.info("Запрос профиля для пользователя: {}", login);
        User user = userRepository.findByLogin(login)
                .orElseThrow(UserNotFoundException::new);

        var posts = publicationRepository.findAllByAuthorOrderByDateOfPublicationDesc(user);
        log.debug("Для профиля {} загружено {} публикаций", login, posts.size());
        return UserProfileDto.builder()
                .user(mapToDto(user))
                .posts(posts)
                .email(user.getEmail())
                .build();
    }

    @Transactional
    @Override
    public void follow(String followerEmail, String followingLogin) {
        log.info("Пользователь {} пытается подписаться на {}", followerEmail, followingLogin);
        User follower = userRepository.findByEmail(followerEmail)
                .orElseThrow(UserNotAuthorizedException::new);
        User following = userRepository.findByLogin(followingLogin)
                .orElseThrow(UserNotFoundException::new);

        if (!followRepository.existsByFollowerAndFollowing(follower, following)) {
            Follow follow = new Follow();
            follow.setFollower(follower);
            follow.setFollowing(following);
            followRepository.save(follow);

            follower.setFollowingCount(follower.getFollowingCount() + 1);
            following.setFollowerCount(following.getFollowerCount() + 1);

            userRepository.save(follower);
            userRepository.save(following);
            log.info("Подписка оформлена: {} -> {}", follower.getLogin(), following.getLogin());
        }
    }

    @Override
    public String getLoginByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        return user.getLogin();
    }

    @Override
    public void updateAvatar(String userEmail, MultipartFile file) {
        log.info("Обновление аватарки для пользователя: {}", userEmail);
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);

        String fileName = fileService.saveUploadFile(file, "avatars");

        if (user.getAvatar() != null && !user.getAvatar().equals("default-avatar.png")) {
            log.debug("Удаление старой аватарки: {}", user.getAvatar());
            fileService.deleteFile(user.getAvatar(), "avatars");
        }

        user.setAvatar(fileName);

        userRepository.save(user);
        log.info("Аватарка пользователя {} обновлена на {}", userEmail, fileName);
    }

    @Override
    public void editProfile(String email, UserEditDto dto) {
        log.info("Редактирование профиля пользователя: {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        if (dto.getName() != null && !dto.getName().isBlank()) {
            user.setName(dto.getName());
        }
        user.setInfo(dto.getInfo());

        MultipartFile file = dto.getAvatar();
        if (file != null && !file.isEmpty()) {
            log.info("Пользователь {} прикрепил новое фото профиля", email);
            if (user.getAvatar() != null) {
                fileService.deleteFile(user.getAvatar(), "avatars");
            }

            String newFileName = fileService.saveUploadFile(file, "avatars");
            user.setAvatar(newFileName);
        }

        userRepository.save(user);
        log.info("Профиль пользователя {} успешно обновлен", email);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    private UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .login(user.getLogin())
                .name(user.getName())
                .avatar(user.getAvatar())
                .followerCount(user.getFollowerCount())
                .followingCount(user.getFollowingCount())
                .info(user.getInfo())
                .build();
    }
}
