package kg.attractor.microgram.repository;

import kg.attractor.microgram.model.Like;
import kg.attractor.microgram.model.Publication;
import kg.attractor.microgram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPublication(User user, Publication publication);
}
