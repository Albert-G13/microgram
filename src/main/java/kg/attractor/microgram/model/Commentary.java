package kg.attractor.microgram.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "commentaries")
public class Commentary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @Column(name = "date_of_publication")
    private LocalDateTime dateOfPublication;
    @ManyToOne
    @JoinColumn(name = "publication_id")
    private Publication publication;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User user;
}
