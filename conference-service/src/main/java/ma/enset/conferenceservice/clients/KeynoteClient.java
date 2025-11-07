package ma.enset.conferenceservice.clients;

import ma.enset.conferenceservice.models.KeynoteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "KEYNOTE-SERVICE", path = "/api/keynotes")
public interface KeynoteClient {

    // Récupérer tous les keynotes
    @GetMapping
    List<KeynoteResponse> getAllKeynotes();

    // Récupérer un keynote par son ID
    @GetMapping("/{id}")
    KeynoteResponse getKeynoteById(@PathVariable Long id);

    // Récupérer un keynote par son email
    @GetMapping("/email/{email}")
    KeynoteResponse getKeynoteByEmail(@PathVariable String email);

    // Récupérer les keynotes par fonction
    @GetMapping("/fonction/{fonction}")
    List<KeynoteResponse> getKeynotesByFonction(@PathVariable String fonction);

    // Vérifier si un keynote existe par ID
    @GetMapping("/{id}/exists")
    Boolean keynoteExists(@PathVariable Long id);
}