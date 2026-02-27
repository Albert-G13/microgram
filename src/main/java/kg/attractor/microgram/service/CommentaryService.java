package kg.attractor.microgram.service;


import kg.attractor.microgram.dto.CommentaryDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentaryService {
    @Transactional
    CommentaryDto addComment(CommentaryDto dto, String userEmail);

    List<CommentaryDto> getCommentsByPublicationId(Long pubId);

    boolean deleteComment(Long commentId, String userEmail);
}
