package ma.enset.keynoteservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.keynoteservice.DTOs.CreateKeynoteRequest;
import ma.enset.keynoteservice.DTOs.KeynoteResponse;
import ma.enset.keynoteservice.DTOs.UpdateKeynoteRequest;
import ma.enset.keynoteservice.entities.Keynote;
import ma.enset.keynoteservice.mappers.KeynoteMapper;
import ma.enset.keynoteservice.repositories.KeynoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class KeynoteService {

    private final KeynoteRepository keynoteRepository;
    private final KeynoteMapper keynoteMapper;

    public KeynoteResponse createKeynote(CreateKeynoteRequest request) {
        log.info("Création d'un nouveau keynote: {}", request.getEmail());

        // Vérifier si l'email existe déjà
        if (keynoteRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Un keynote avec cet email existe déjà");
        }

        Keynote keynote = keynoteMapper.toEntity(request);
        Keynote savedKeynote = keynoteRepository.save(keynote);

        log.info("Keynote créé avec ID: {}", savedKeynote.getId());
        return keynoteMapper.toResponse(savedKeynote);
    }

    public List<KeynoteResponse> getAllKeynotes() {
        log.info("Récupération de tous les keynotes");
        return keynoteRepository.findAll()
                .stream()
                .map(keynoteMapper::toResponse)
                .collect(Collectors.toList());
    }

    public KeynoteResponse getKeynoteById(Long id) {
        log.info("Récupération du keynote avec ID: {}", id);
        Keynote keynote = keynoteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Keynote non trouvé avec ID: " + id));
        return keynoteMapper.toResponse(keynote);
    }

    public KeynoteResponse updateKeynote(Long id, UpdateKeynoteRequest request) {
        log.info("Mise à jour du keynote avec ID: {}", id);

        Keynote keynote = keynoteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Keynote non trouvé avec ID: " + id));

        // Vérifier si le nouvel email existe déjà (s'il est fourni)
        if (request.getEmail() != null &&
                !request.getEmail().equals(keynote.getEmail()) &&
                keynoteRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Un keynote avec cet email existe déjà");
        }

        keynoteMapper.updateEntityFromRequest(request, keynote);
        Keynote updatedKeynote = keynoteRepository.save(keynote);

        log.info("Keynote mis à jour avec ID: {}", id);
        return keynoteMapper.toResponse(updatedKeynote);
    }

    public void deleteKeynote(Long id) {
        log.info("Suppression du keynote avec ID: {}", id);

        if (!keynoteRepository.existsById(id)) {
            throw new RuntimeException("Keynote non trouvé avec ID: " + id);
        }

        keynoteRepository.deleteById(id);
        log.info("Keynote supprimé avec ID: {}", id);
    }

    public KeynoteResponse getKeynoteByEmail(String email) {
        log.info("Récupération du keynote avec email: {}", email);
        Keynote keynote = keynoteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Keynote non trouvé avec email: " + email));
        return keynoteMapper.toResponse(keynote);
    }

    public List<KeynoteResponse> getKeynotesByFonction(String fonction) {
        log.info("Récupération des keynotes avec fonction: {}", fonction);
        return keynoteRepository.findByFonction(fonction)
                .stream()
                .map(keynoteMapper::toResponse)
                .collect(Collectors.toList());
    }
}