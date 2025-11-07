package ma.enset.conferenceservice.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import ma.enset.conferenceservice.enums.TypeConference;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "conferences")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeConference type;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Integer duree; // en minutes

    private Integer nombreInscrits;
    private Double score;
    private Long keynoteId;

    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    @JsonManagedReference
    private List<Review> reviews = new ArrayList<>();


    public void addReview(Review review) {
        reviews.add(review);
        review.setConference(this);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
        review.setConference(null);
    }
}
