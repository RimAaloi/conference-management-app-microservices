package ma.enset.conferenceservice.mappers;

import lombok.RequiredArgsConstructor;
import ma.enset.conferenceservice.DTOs.ConferenceResponse;
import ma.enset.conferenceservice.DTOs.CreateConferenceRequest;
import ma.enset.conferenceservice.DTOs.ReviewResponse;
import ma.enset.conferenceservice.DTOs.UpdateConferenceRequest;
import ma.enset.conferenceservice.entities.Conference;
import ma.enset.conferenceservice.models.KeynoteResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ConferenceMapper {

    private final ReviewMapper reviewMapper;

    public Conference toEntity(CreateConferenceRequest request) {  // ⚠️ Corrigé le package
        if (request == null) return null;

        return Conference.builder()
                .titre(request.getTitre())
                .type(request.getType())
                .date(request.getDate())
                .duree(request.getDuree())
                .nombreInscrits(request.getNombreInscrits())
                .keynoteId(request.getKeynoteId())
                .score(0.0)
                .build();
    }

    public ConferenceResponse toResponse(Conference conference, KeynoteResponse keynote) {
        if (conference == null) return null;

        List<ReviewResponse> reviewResponses = conference.getReviews().stream()
                .map(reviewMapper::toResponse)
                .collect(Collectors.toList());

        return new ConferenceResponse(
                conference.getId(),
                conference.getTitre(),
                conference.getType(),
                conference.getDate(),
                conference.getDuree(),
                conference.getNombreInscrits(),
                conference.getScore(),
                conference.getKeynoteId(),
                keynote,
                reviewResponses
        );
    }

    public void updateEntityFromRequest(UpdateConferenceRequest request, Conference conference) {
        if (request == null || conference == null) return;

        if (request.getTitre() != null) {
            conference.setTitre(request.getTitre());
        }
        if (request.getType() != null) {
            conference.setType(request.getType());
        }
        if (request.getDate() != null) {
            conference.setDate(request.getDate());
        }
        if (request.getDuree() != null) {
            conference.setDuree(request.getDuree());
        }
        if (request.getNombreInscrits() != null) {
            conference.setNombreInscrits(request.getNombreInscrits());
        }
        if (request.getKeynoteId() != null) {
            conference.setKeynoteId(request.getKeynoteId());
        }
    }
}