package ma.enset.conferenceservice.DTOs;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.conferenceservice.enums.TypeConference;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateConferenceRequest {

    @Size(min = 5, max = 255, message = "Le titre doit contenir entre 5 et 255 caractères")
    private String titre;

    private TypeConference type;

    private LocalDate date;

    @Min(value = 30, message = "La durée minimum est de 30 minutes")
    @Max(value = 480, message = "La durée maximum est de 480 minutes")
    private Integer duree;

    @Min(value = 0, message = "Le nombre d'inscrits ne peut pas être négatif")
    private Integer nombreInscrits;

    private Long keynoteId;
}