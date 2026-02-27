package kg.attractor.microgram.controller;

import kg.attractor.microgram.dto.UserEditDto;
import kg.attractor.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/search")
    public String searchPage(@RequestParam(name = "query", required = false) String query, Model model) {
        if (query != null && !query.isBlank()) {
            // Вызываем сервис поиска
            var users = userService.searchUsers(query);
            model.addAttribute("users", users);
            model.addAttribute("query", query);
        }
        return "profile/search";
    }

    @GetMapping("/profile")
    public String myProfile(Principal principal) {
        String login = userService.getLoginByEmail(principal.getName());
        return "redirect:/profile/" + login;
    }
    @GetMapping("/profile/{login}")
    public String profile(@PathVariable String login, Model model, Principal principal) {
        var profileData = userService.getProfile(login);
        model.addAttribute("profile", profileData.getUser());
        model.addAttribute("posts", profileData.getPosts());
        System.out.println("Principal Name: " + principal.getName());
        System.out.println("Profile User Email: " + profileData.getUser().getEmail());
        System.out.println("Are they equal? " + principal.getName().equals(profileData.getUser().getEmail()));

        boolean isMyProfile = principal != null && principal.getName().equals(profileData.getUser().getEmail());
        model.addAttribute("isMyProfile", isMyProfile);

        return "profile/profile";
    }

    @PostMapping("/follow/{login}")
    public String followUser(@PathVariable String login, Principal principal) {
        userService.follow(principal.getName(), login);

        return "redirect:/profile/" + login;
    }
    @PostMapping("/profile/update-avatar")
    public String updateAvatar(@RequestParam("file") MultipartFile file, Principal principal) {
        if (!file.isEmpty()) {
            userService.updateAvatar(principal.getName(), file);
        }
        String login = userService.getLoginByEmail(principal.getName());
        return "redirect:/profile/" + login;
    }

    @GetMapping("/profile/edit")
    public String editPage(Model model, Principal principal) {
        var user = userService.getByEmail(principal.getName());
        model.addAttribute("user", user);
        return "profile/edit_profile";
    }

    @PostMapping("/profile/edit")
    public String updateProfile(@ModelAttribute UserEditDto dto, Principal principal) {
        userService.editProfile(principal.getName(), dto);
        var user = userService.getByEmail(principal.getName());
        return "redirect:/profile/" + user.getLogin();
    }
}
