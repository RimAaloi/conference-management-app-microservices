package ma.enset.conferenceservice.repositories;

import ma.enset.conferenceservice.entities.Conference;
import ma.enset.conferenceservice.enums.TypeConference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Long> {

    // Trouver par type
    List<Conference> findByType(TypeConference type);

    // Trouver par date
    List<Conference> findByDate(LocalDate date);

    // Trouver les conférences après une certaine date
    List<Conference> findByDateAfter(LocalDate date);

    // Trouver par titre (contient)
    List<Conference> findByTitreContainingIgnoreCase(String titre);

    // Trouver les conférences avec un score minimum
    List<Conference> findByScoreGreaterThanEqual(Double score);

    // Calculer le score moyen d'une conférence
    @Query("SELECT AVG(r.note) FROM Review r WHERE r.conference.id = :conferenceId")
    Double calculateAverageScore(@Param("conferenceId") Long conferenceId);

    // Compter le nombre de reviews par conférence
    @Query("SELECT COUNT(r) FROM Review r WHERE r.conference.id = :conferenceId")
    Long countReviewsByConferenceId(@Param("conferenceId") Long conferenceId);
}