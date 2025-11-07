package ma.enset.conferenceservice.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private Long id;
    private LocalDateTime date;
    private String texte;
    private Integer note;
    private Long conferenceId;
}