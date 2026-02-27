package kg.attractor.microgram.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PublicationDto {
    private Long id;
    private String image;
    private String description;
    private LocalDateTime dateOfPublication;
    private Long likesCount;
    private Long commentaryCount;
    private String authorLogin;
    private String authorAvatar;
    private List<CommentaryDto> comments;
}
