package kg.attractor.microgram.service;

import kg.attractor.microgram.dto.UserDto;
import kg.attractor.microgram.dto.UserEditDto;
import kg.attractor.microgram.dto.UserProfileDto;
import kg.attractor.microgram.dto.UserRegisterDto;
import kg.attractor.microgram.model.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    void register(UserRegisterDto dto);

    List<UserDto> searchUsers(String query);

    UserProfileDto getProfile(String login);

    @Transactional
    void follow(String followerEmail, String followingLogin);

    String getLoginByEmail(String email);

    void updateAvatar(String userEmail, MultipartFile file);

    void editProfile(String email, UserEditDto dto);

    User getByEmail(String email);
}
