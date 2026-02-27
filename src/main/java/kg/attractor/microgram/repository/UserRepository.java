package kg.attractor.microgram.repository;

import kg.attractor.microgram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByLogin(String login);

    List<User> findByNameContainingIgnoreCaseOrLoginContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String name, String login, String email);

    List<User> findAllByLoginContainingIgnoreCaseOrNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String login, String name, String email);
}
