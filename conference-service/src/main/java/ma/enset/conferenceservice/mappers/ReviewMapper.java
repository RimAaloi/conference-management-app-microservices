package ma.enset.conferenceservice.mappers;

import ma.enset.conferenceservice.DTOs.CreateReviewRequest;
import ma.enset.conferenceservice.DTOs.ReviewResponse;
import ma.enset.conferenceservice.entities.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public Review toEntity(CreateReviewRequest request) {
        if (request == null) return null;

        return Review.builder()
                .texte(request.getTexte())
                .note(request.getNote())
                .build();
    }

    public ReviewResponse toResponse(Review review) {
        if (review == null) return null;

        return new ReviewResponse(
                review.getId(),
                review.getDate(),
                review.getTexte(),
                review.getNote(),
                review.getConference() != null ? review.getConference().getId() : null
        );
    }
}