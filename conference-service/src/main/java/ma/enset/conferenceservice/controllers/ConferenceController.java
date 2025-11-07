package ma.enset.conferenceservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.enset.conferenceservice.DTOs.ConferenceResponse;
import ma.enset.conferenceservice.DTOs.CreateConferenceRequest;
import ma.enset.conferenceservice.DTOs.CreateReviewRequest;
import ma.enset.conferenceservice.DTOs.ReviewResponse;
import ma.enset.conferenceservice.DTOs.UpdateConferenceRequest;
import ma.enset.conferenceservice.enums.TypeConference;
import ma.enset.conferenceservice.services.ConferenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conferences")
@RequiredArgsConstructor
@Tag(name = "Conference API", description = "API de gestion des conférences")
public class ConferenceController {

    private final ConferenceService conferenceService;

    @PostMapping
    @Operation(summary = "Créer une nouvelle conférence")
    public ResponseEntity<ConferenceResponse> createConference(@Valid @RequestBody CreateConferenceRequest request) {
        ConferenceResponse response = conferenceService.createConference(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Récupérer toutes les conférences")
    public ResponseEntity<List<ConferenceResponse>> getAllConferences() {
        List<ConferenceResponse> conferences = conferenceService.getAllConferences();
        return ResponseEntity.ok(conferences);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une conférence par son ID")
    public ResponseEntity<ConferenceResponse> getConferenceById(@PathVariable Long id) {
        ConferenceResponse conference = conferenceService.getConferenceById(id);
        return ResponseEntity.ok(conference);
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "Récupérer les conférences par type")
    public ResponseEntity<List<ConferenceResponse>> getConferencesByType(@PathVariable TypeConference type) {
        List<ConferenceResponse> conferences = conferenceService.getConferencesByType(type);
        return ResponseEntity.ok(conferences);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une conférence")
    public ResponseEntity<ConferenceResponse> updateConference(
            @PathVariable Long id,
            @Valid @RequestBody UpdateConferenceRequest request) {
        ConferenceResponse updatedConference = conferenceService.updateConference(id, request);
        return ResponseEntity.ok(updatedConference);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une conférence")
    public ResponseEntity<Void> deleteConference(@PathVariable Long id) {
        conferenceService.deleteConference(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reviews")
    @Operation(summary = "Ajouter une review à une conférence")
    public ResponseEntity<ReviewResponse> addReviewToConference(@Valid @RequestBody CreateReviewRequest request) {
        ReviewResponse response = conferenceService.addReviewToConference(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}