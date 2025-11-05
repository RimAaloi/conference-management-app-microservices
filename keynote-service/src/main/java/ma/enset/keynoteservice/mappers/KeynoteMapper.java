package ma.enset.keynoteservice.mappers;

import ma.enset.keynoteservice.DTOs.CreateKeynoteRequest;
import ma.enset.keynoteservice.DTOs.KeynoteResponse;
import ma.enset.keynoteservice.DTOs.UpdateKeynoteRequest;
import ma.enset.keynoteservice.entities.Keynote;
import org.springframework.stereotype.Component;

@Component
public class KeynoteMapper {

    public Keynote toEntity(CreateKeynoteRequest request) {
        if (request == null) return null;

        return Keynote.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .fonction(request.getFonction())
                .build();
    }

    public KeynoteResponse toResponse(Keynote keynote) {
        if (keynote == null) return null;

        return new KeynoteResponse(
                keynote.getId(),
                keynote.getNom(),
                keynote.getPrenom(),
                keynote.getEmail(),
                keynote.getFonction()
        );
    }

    public void updateEntityFromRequest(UpdateKeynoteRequest request, Keynote keynote) {
        if (request == null || keynote == null) return;

        if (request.getNom() != null) {
            keynote.setNom(request.getNom());
        }
        if (request.getPrenom() != null) {
            keynote.setPrenom(request.getPrenom());
        }
        if (request.getEmail() != null) {
            keynote.setEmail(request.getEmail());
        }
        if (request.getFonction() != null) {
            keynote.setFonction(request.getFonction());
        }
    }
}
