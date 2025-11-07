package ma.enset.conferenceservice.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.conferenceservice.enums.TypeConference;
import ma.enset.conferenceservice.models.KeynoteResponse;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConferenceResponse {
    private Long id;
    private String titre;
    private TypeConference type;
    private LocalDate date;
    private Integer duree;
    private Integer nombreInscrits;
    private Double score;
    private Long keynoteId;
    private KeynoteResponse keynote;
    private List<ReviewResponse> reviews;
}