package kg.attractor.microgram.repository;

import kg.attractor.microgram.model.Follow;
import kg.attractor.microgram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findAllByFollower(User follower);

    boolean existsByFollowerAndFollowing(User follower, User following);
}
