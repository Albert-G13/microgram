package kg.attractor.microgram.service;


import jakarta.transaction.Transactional;

public interface FollowService {
    @Transactional
    void follow(Long followerId, Long followingId);
}
