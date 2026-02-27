package kg.attractor.microgram.dto;

import kg.attractor.microgram.model.Publication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {
    private UserDto user;
    private List<Publication> posts;
    private String email;
}
