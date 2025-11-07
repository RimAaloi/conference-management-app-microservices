package ma.enset.conferenceservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.conferenceservice.DTOs.ConferenceResponse;
import ma.enset.conferenceservice.DTOs.CreateConferenceRequest; // ⚠️ IMPORT CORRECT
import ma.enset.conferenceservice.DTOs.CreateReviewRequest;
import ma.enset.conferenceservice.DTOs.ReviewResponse;
import ma.enset.conferenceservice.DTOs.UpdateConferenceRequest;
import ma.enset.conferenceservice.clients.KeynoteClient;
import ma.enset.conferenceservice.entities.Conference;
import ma.enset.conferenceservice.entities.Review;
import ma.enset.conferenceservice.enums.TypeConference;
import ma.enset.conferenceservice.mappers.ConferenceMapper;
import ma.enset.conferenceservice.mappers.ReviewMapper;
import ma.enset.conferenceservice.models.KeynoteResponse;
import ma.enset.conferenceservice.repositories.ConferenceRepository;
import ma.enset.conferenceservice.repositories.ReviewRepository;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ConferenceService {

    private final ConferenceRepository conferenceRepository;
    private final ReviewRepository reviewRepository;
    private final ConferenceMapper conferenceMapper;
    private final ReviewMapper reviewMapper;
    private final KeynoteClient keynoteClient;
    private final CircuitBreakerFactory circuitBreakerFactory;

    // ⚠️ CORRIGÉ : Utilise le bon package
    public ConferenceResponse createConference(CreateConferenceRequest request) {
        log.info("Création d'une nouvelle conférence: {}", request.getTitre());

        // Vérifier si le keynote existe
        if (!keynoteExists(request.getKeynoteId())) {
            throw new RuntimeException("Keynote non trouvé avec ID: " + request.getKeynoteId());
        }

        Conference conference = conferenceMapper.toEntity(request);
        Conference savedConference = conferenceRepository.save(conference);

        KeynoteResponse keynote = getKeynoteById(request.getKeynoteId());

        log.info("Conférence créée avec ID: {}", savedConference.getId());
        return conferenceMapper.toResponse(savedConference, keynote);
    }

    public List<ConferenceResponse> getAllConferences() {
        log.info("Récupération de toutes les conférences");

        return conferenceRepository.findAll().stream()
                .map(conference -> {
                    KeynoteResponse keynote = getKeynoteById(conference.getKeynoteId());
                    return conferenceMapper.toResponse(conference, keynote);
                })
                .collect(Collectors.toList());
    }

    public ConferenceResponse getConferenceById(Long id) {
        log.info("Récupération de la conférence avec ID: {}", id);

        Conference conference = conferenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conférence non trouvée avec ID: " + id));

        KeynoteResponse keynote = getKeynoteById(conference.getKeynoteId());

        return conferenceMapper.toResponse(conference, keynote);
    }

    public ConferenceResponse updateConference(Long id, UpdateConferenceRequest request) {
        log.info("Mise à jour de la conférence avec ID: {}", id);

        Conference conference = conferenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conférence non trouvée avec ID: " + id));

        // Vérifier le keynote si fourni
        if (request.getKeynoteId() != null && !keynoteExists(request.getKeynoteId())) {
            throw new RuntimeException("Keynote non trouvé avec ID: " + request.getKeynoteId());
        }

        conferenceMapper.updateEntityFromRequest(request, conference);
        Conference updatedConference = conferenceRepository.save(conference);

        KeynoteResponse keynote = getKeynoteById(updatedConference.getKeynoteId());

        log.info("Conférence mise à jour avec ID: {}", id);
        return conferenceMapper.toResponse(updatedConference, keynote);
    }

    public void deleteConference(Long id) {
        log.info("Suppression de la conférence avec ID: {}", id);

        if (!conferenceRepository.existsById(id)) {
            throw new RuntimeException("Conférence non trouvée avec ID: " + id);
        }

        conferenceRepository.deleteById(id);
        log.info("Conférence supprimée avec ID: {}", id);
    }

    public ReviewResponse addReviewToConference(CreateReviewRequest request) {
        log.info("Ajout d'une review à la conférence ID: {}", request.getConferenceId());

        Conference conference = conferenceRepository.findById(request.getConferenceId())
                .orElseThrow(() -> new RuntimeException("Conférence non trouvée avec ID: " + request.getConferenceId()));

        Review review = reviewMapper.toEntity(request);
        conference.addReview(review);

        Review savedReview = reviewRepository.save(review);

        // Mettre à jour le score moyen de la conférence
        updateConferenceScore(conference.getId());

        log.info("Review ajoutée avec ID: {}", savedReview.getId());
        return reviewMapper.toResponse(savedReview);
    }

    public List<ConferenceResponse> getConferencesByType(TypeConference type) {
        log.info("Récupération des conférences de type: {}", type);

        return conferenceRepository.findByType(type).stream()
                .map(conference -> {
                    KeynoteResponse keynote = getKeynoteById(conference.getKeynoteId());
                    return conferenceMapper.toResponse(conference, keynote);
                })
                .collect(Collectors.toList());
    }

    private void updateConferenceScore(Long conferenceId) {
        Double averageScore = reviewRepository.findAverageNoteByConferenceId(conferenceId);
        if (averageScore != null) {
            Conference conference = conferenceRepository.findById(conferenceId).orElseThrow();
            conference.setScore(Math.round(averageScore * 100.0) / 100.0); // Arrondir à 2 décimales
            conferenceRepository.save(conference);
            log.info("Score de la conférence {} mis à jour: {}", conferenceId, conference.getScore());
        }
    }

    private KeynoteResponse getKeynoteById(Long keynoteId) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("keynoteClient");

        return circuitBreaker.run(
                () -> keynoteClient.getKeynoteById(keynoteId),
                throwable -> {
                    log.error("Erreur lors de la récupération du keynote: {}", throwable.getMessage());
                    return getFallbackKeynote();
                }
        );
    }

    private boolean keynoteExists(Long keynoteId) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("keynoteClient");

        return circuitBreaker.run(
                () -> {
                    KeynoteResponse keynote = keynoteClient.getKeynoteById(keynoteId);
                    return keynote != null;
                },
                throwable -> {
                    log.error("Erreur lors de la vérification du keynote: {}", throwable.getMessage());
                    return false;
                }
        );
    }

    private KeynoteResponse getFallbackKeynote() {
        return new KeynoteResponse(
                -1L,
                "Service Indisponible",
                "Veuillez réessayer plus tard",
                "service.indisponible@email.com",
                "Service Temporairement Indisponible"
        );
    }
}