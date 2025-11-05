package ma.enset.keynoteservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.enset.keynoteservice.DTOs.CreateKeynoteRequest;
import ma.enset.keynoteservice.DTOs.KeynoteResponse;
import ma.enset.keynoteservice.DTOs.UpdateKeynoteRequest;
import ma.enset.keynoteservice.services.KeynoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/keynotes")
@RequiredArgsConstructor
@Tag(name = "Keynote API", description = "API de gestion des keynotes")
public class KeynoteController {

    private final KeynoteService keynoteService;

    @PostMapping
    @Operation(summary = "Créer un nouveau keynote")
    public ResponseEntity<KeynoteResponse> createKeynote(@Valid @RequestBody CreateKeynoteRequest request) {
        KeynoteResponse response = keynoteService.createKeynote(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Récupérer tous les keynotes")
    public ResponseEntity<List<KeynoteResponse>> getAllKeynotes() {
        List<KeynoteResponse> keynotes = keynoteService.getAllKeynotes();
        return ResponseEntity.ok(keynotes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un keynote par son ID")
    public ResponseEntity<KeynoteResponse> getKeynoteById(@PathVariable Long id) {
        KeynoteResponse keynote = keynoteService.getKeynoteById(id);
        return ResponseEntity.ok(keynote);
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Récupérer un keynote par son email")
    public ResponseEntity<KeynoteResponse> getKeynoteByEmail(@PathVariable String email) {
        KeynoteResponse keynote = keynoteService.getKeynoteByEmail(email);
        return ResponseEntity.ok(keynote);
    }

    @GetMapping("/fonction/{fonction}")
    @Operation(summary = "Récupérer les keynotes par fonction")
    public ResponseEntity<List<KeynoteResponse>> getKeynotesByFonction(@PathVariable String fonction) {
        List<KeynoteResponse> keynotes = keynoteService.getKeynotesByFonction(fonction);
        return ResponseEntity.ok(keynotes);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un keynote")
    public ResponseEntity<KeynoteResponse> updateKeynote(
            @PathVariable Long id,
            @Valid @RequestBody UpdateKeynoteRequest request) {
        KeynoteResponse updatedKeynote = keynoteService.updateKeynote(id, request);
        return ResponseEntity.ok(updatedKeynote);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un keynote")
    public ResponseEntity<Void> deleteKeynote(@PathVariable Long id) {
        keynoteService.deleteKeynote(id);
        return ResponseEntity.noContent().build();
    }
}