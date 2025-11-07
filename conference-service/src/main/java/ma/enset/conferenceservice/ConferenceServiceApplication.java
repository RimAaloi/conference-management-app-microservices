package ma.enset.conferenceservice;

import lombok.RequiredArgsConstructor;
import ma.enset.conferenceservice.entities.Conference;
import ma.enset.conferenceservice.entities.Review;
import ma.enset.conferenceservice.enums.TypeConference;
import ma.enset.conferenceservice.repositories.ConferenceRepository;
import ma.enset.conferenceservice.repositories.ReviewRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@EnableFeignClients
@RequiredArgsConstructor
public class ConferenceServiceApplication {

    private  final ConferenceRepository conferenceRepository;
    private final ReviewRepository reviewRepository;

    public static void main(String[] args) {
        SpringApplication.run(ConferenceServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            System.out.println("=== DÉBUT DU REMPLISSAGE DE LA BASE CONFERENCE-SERVICE ===");

            // Vérifier si la base est déjà remplie
            if (conferenceRepository.count() == 0) {
                createTestData();
                System.out.println("=== DONNÉES DE TEST CRÉÉES AVEC SUCCÈS ===");
            } else {
                System.out.println("=== BASE DÉJÀ REMPLIE AVEC " + conferenceRepository.count() + " CONFÉRENCES ===");
            }
        };
    }

    private void createTestData() {
        // Création des conférences
        Conference conference1 = Conference.builder()
                .titre("Architecture Micro-services avec Spring Cloud")
                .type(TypeConference.ACADEMIQUE)
                .date(LocalDate.now().plusDays(15))
                .duree(120)
                .nombreInscrits(150)
                .score(4.2)
                .keynoteId(1L)
                .build();

        Conference conference2 = Conference.builder()
                .titre("Data Science et Machine Learning en Production")
                .type(TypeConference.ACADEMIQUE)
                .date(LocalDate.now().plusDays(30))
                .duree(90)
                .nombreInscrits(200)
                .score(4.5)
                .keynoteId(2L)
                .build();

        Conference conference3 = Conference.builder()
                .titre("DevOps et CI/CD avec Docker et Kubernetes")
                .type(TypeConference.COMMERCIALE)
                .date(LocalDate.now().plusDays(45))
                .duree(180)
                .nombreInscrits(100)
                .score(4.0)
                .keynoteId(3L)
                .build();

        Conference conference4 = Conference.builder()
                .titre("Gestion de Produit Agile")
                .type(TypeConference.COMMERCIALE)
                .date(LocalDate.now().plusDays(60))
                .duree(60)
                .nombreInscrits(80)
                .score(4.3)
                .keynoteId(4L)
                .build();

        Conference conference5 = Conference.builder()
                .titre("Cybersécurité Cloud Native")
                .type(TypeConference.ACADEMIQUE)
                .date(LocalDate.now().plusDays(75))
                .duree(150)
                .nombreInscrits(120)
                .score(4.7)
                .keynoteId(5L)
                .build();

        // Sauvegarder les conférences d'abord
        List<Conference> savedConferences = conferenceRepository.saveAll(
                List.of(conference1, conference2, conference3, conference4, conference5)
        );

        // Création des reviews en utilisant addReview() pour la relation bidirectionnelle
        Review review1 = Review.builder()
                .texte("Excellente conférence! Le contenu était très pertinent et bien structuré.")
                .note(5)
                .build();
        savedConferences.get(0).addReview(review1); // ⚠️ Utilise addReview()

        Review review2 = Review.builder()
                .texte("Bon contenu mais le rythme était un peu trop rapide.")
                .note(3)
                .build();
        savedConferences.get(0).addReview(review2);

        Review review3 = Review.builder()
                .texte("Très bon speaker, des exemples concrets et utiles.")
                .note(4)
                .build();
        savedConferences.get(0).addReview(review3);

        Review review4 = Review.builder()
                .texte("Conférence exceptionnelle, j'ai beaucoup appris.")
                .note(5)
                .build();
        savedConferences.get(0).addReview(review4);

        Review review5 = Review.builder()
                .texte("Contenu intéressant mais trop technique pour les débutants.")
                .note(3)
                .build();
        savedConferences.get(0).addReview(review5);

        Review review6 = Review.builder()
                .texte("Pratique et directement applicable dans mon travail.")
                .note(4)
                .build();
        savedConferences.get(0).addReview(review6);

        // Sauvegarder les reviews
        List<Review> savedReviews = reviewRepository.saveAll(
                List.of(review1, review2, review3, review4, review5, review6)
        );


        // Recharger les conférences pour avoir les reviews
        savedConferences = conferenceRepository.findAll();


        System.out.println(savedReviews.size() + " reviews créées");

        // Afficher le résumé
        System.out.println("\n=== RÉSUMÉ DES DONNÉES CRÉÉES ===");
        savedConferences.forEach(conf -> {
            long reviewCount = conf.getReviews().size();
            System.out.println("Conférence: " + conf.getTitre());
            System.out.println("  - Type: " + conf.getType());
            System.out.println("  - Date: " + conf.getDate());
            System.out.println("  - Durée: " + conf.getDuree() + " min");
            System.out.println("  - Inscrits: " + conf.getNombreInscrits());
            System.out.println("  - Score: " + conf.getScore());
            System.out.println("  - Keynote ID: " + conf.getKeynoteId());
            System.out.println("  - Reviews: " + reviewCount);
            System.out.println();
        });
    }
}