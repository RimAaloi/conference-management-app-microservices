package ma.enset.keynoteservice.repositories;

import ma.enset.keynoteservice.entities.Keynote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KeynoteRepository extends JpaRepository<Keynote, Long> {

    // Trouver par email
    Optional<Keynote> findByEmail(String email);

    // Trouver par nom
    List<Keynote> findByNom(String nom);

    // Trouver par fonction
    List<Keynote> findByFonction(String fonction);

    // Vérifier si un email existe
    boolean existsByEmail(String email);
}