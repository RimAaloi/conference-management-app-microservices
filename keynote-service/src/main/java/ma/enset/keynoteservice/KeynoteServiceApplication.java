package ma.enset.keynoteservice;

import ma.enset.keynoteservice.entities.Keynote;
import ma.enset.keynoteservice.repositories.KeynoteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class KeynoteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeynoteServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(KeynoteRepository keynoteRepository) {
        return args -> {
            // Vider la base d'abord (optionnel)
            // keynoteRepository.deleteAll();

            // Données de test pour les keynotes
            List<Keynote> keynotes = List.of(
                    Keynote.builder()
                            .nom("Martin")
                            .prenom("Pierre")
                            .email("pierre.martin@email.com")
                            .fonction("Architecte Logiciel")
                            .build(),

                    Keynote.builder()
                            .nom("Dubois")
                            .prenom("Marie")
                            .email("marie.dubois@email.com")
                            .fonction("Data Scientist")
                            .build(),

                    Keynote.builder()
                            .nom("Bernard")
                            .prenom("Jean")
                            .email("jean.bernard@email.com")
                            .fonction("DevOps Engineer")
                            .build(),

                    Keynote.builder()
                            .nom("Moreau")
                            .prenom("Sophie")
                            .email("sophie.moreau@email.com")
                            .fonction("Product Manager")
                            .build(),

                    Keynote.builder()
                            .nom("Laurent")
                            .prenom("Thomas")
                            .email("thomas.laurent@email.com")
                            .fonction("CTO")
                            .build(),

                    Keynote.builder()
                            .nom("Simon")
                            .prenom("Laura")
                            .email("laura.simon@email.com")
                            .fonction("AI Researcher")
                            .build(),

                    Keynote.builder()
                            .nom("Michel")
                            .prenom("David")
                            .email("david.michel@email.com")
                            .fonction("Security Expert")
                            .build(),

                    Keynote.builder()
                            .nom("Leroy")
                            .prenom("Catherine")
                            .email("catherine.leroy@email.com")
                            .fonction("Cloud Architect")
                            .build()
            );

            // Sauvegarder seulement si la base est vide
            if (keynoteRepository.count() == 0) {
                keynoteRepository.saveAll(keynotes);
                System.out.println("=== " + keynotes.size() + " KEYNOTES DE TEST CRÉÉS ===");

                // Afficher les keynotes créés
                keynoteRepository.findAll().forEach(keynote -> {
                    System.out.println("Keynote: " + keynote.getPrenom() + " " + keynote.getNom() +
                            " - " + keynote.getFonction());
                });
            } else {
                System.out.println("=== BASE DÉJÀ REMPLIE AVEC " + keynoteRepository.count() + " KEYNOTES ===");
            }
        };



    }
}