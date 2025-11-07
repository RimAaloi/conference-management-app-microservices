package ma.enset.conferenceservice.repositories;

import ma.enset.conferenceservice.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Trouver toutes les reviews d'une conférence
    List<Review> findByConferenceId(Long conferenceId);

    // Trouver les reviews par note
    List<Review> findByNote(Integer note);

    // Trouver les reviews avec une note supérieure ou égale
    List<Review> findByNoteGreaterThanEqual(Integer note);

    // Calculer la moyenne des notes d'une conférence
    @Query("SELECT AVG(r.note) FROM Review r WHERE r.conference.id = :conferenceId")
    Double findAverageNoteByConferenceId(@Param("conferenceId") Long conferenceId);

    // Compter le nombre de reviews par note pour une conférence
    @Query("SELECT r.note, COUNT(r) FROM Review r WHERE r.conference.id = :conferenceId GROUP BY r.note")
    List<Object[]> countReviewsByNoteForConference(@Param("conferenceId") Long conferenceId);
}