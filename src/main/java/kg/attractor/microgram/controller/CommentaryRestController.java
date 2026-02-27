package kg.attractor.microgram.controller;

import kg.attractor.microgram.dto.CommentaryDto;
import kg.attractor.microgram.service.CommentaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentaryRestController {
    private final CommentaryService commentaryService;

    @PostMapping
    public ResponseEntity<CommentaryDto> addComment(@RequestBody CommentaryDto dto, Principal principal) {
        CommentaryDto savedComment = commentaryService.addComment(dto, principal.getName());
        return ResponseEntity.ok(savedComment);
    }
    @GetMapping
    public List<CommentaryDto> getComments(@RequestParam Long publicationId) {
        return commentaryService.getCommentsByPublicationId(publicationId);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        boolean deleted = commentaryService.deleteComment(id, principal.getName());

        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Вы не можете удалить этот комментарий");
        }
    }
}
