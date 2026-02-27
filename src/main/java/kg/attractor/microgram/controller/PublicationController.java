package kg.attractor.microgram.controller;

import kg.attractor.microgram.dto.PublicationCreateDto;
import kg.attractor.microgram.service.PublicationService;
import kg.attractor.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class PublicationController {
    private final PublicationService publicationService;
    private final UserService userService;

    @GetMapping("/feed")
    public String feed(Model model, Principal principal) {
        if (principal != null) {
            var posts = publicationService.getFeed(principal.getName());
            var user = userService.getLoginByEmail(principal.getName());

            model.addAttribute("posts", posts);

            model.addAttribute("user", user);
        }
        return "content/feed";
    }
    @GetMapping("/publications/add")
    public String createPage() {
        return "content/add_publication";
    }
    @PostMapping("/publications/add")
    public String createPublication(@ModelAttribute PublicationCreateDto dto, Principal principal) {
        publicationService.createPublication(dto, principal.getName());
        return "redirect:/feed";
    }
}