package kg.attractor.microgram.service;


import jakarta.transaction.Transactional;

public interface LikeService {
    @Transactional
    Long toggleLike(Long publicationId, String userEmail);
}
