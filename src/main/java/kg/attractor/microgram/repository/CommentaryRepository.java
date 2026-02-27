package kg.attractor.microgram.repository;

import kg.attractor.microgram.model.Commentary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentaryRepository extends JpaRepository<Commentary, Long> {
}
