package kg.attractor.microgram.controller;

import kg.attractor.microgram.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeRestController {
    private final LikeService likeService;

    @PostMapping("/{pubId}")
    public ResponseEntity<Long> like(@PathVariable Long pubId, Principal principal) {
        Long newCount = likeService.toggleLike(pubId, principal.getName());
        return ResponseEntity.ok(newCount);
    }
}
