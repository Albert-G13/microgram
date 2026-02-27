package kg.attractor.microgram.repository;

import kg.attractor.microgram.model.Publication;
import kg.attractor.microgram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    List<Publication> findAllByAuthorInOrderByDateOfPublicationDesc(List<User> authors);
    List<Publication> findAllByAuthorOrderByDateOfPublicationDesc(User author);
}
