package kg.attractor.microgram.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentaryDto {
    private Long id;
    private String content;
    private LocalDateTime dateOfPublication;
    private Long authorId;
    private Long publicationId;
    private String authorLogin;
}
