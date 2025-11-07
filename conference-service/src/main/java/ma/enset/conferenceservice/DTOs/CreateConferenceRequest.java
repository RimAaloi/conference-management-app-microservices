package ma.enset.conferenceservice.DTOs;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.conferenceservice.enums.TypeConference;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateConferenceRequest {

    @NotBlank(message = "Le titre est obligatoire")
    @Size(min = 5, max = 255, message = "Le titre doit contenir entre 5 et 255 caractères")
    private String titre;

    @NotNull(message = "Le type est obligatoire")
    private TypeConference type;

    @NotNull(message = "La date est obligatoire")
    @Future(message = "La date doit être dans le futur")
    private LocalDate date;

    @NotNull(message = "La durée est obligatoire")
    @Min(value = 30, message = "La durée minimum est de 30 minutes")
    @Max(value = 480, message = "La durée maximum est de 480 minutes")
    private Integer duree;

    @Min(value = 0, message = "Le nombre d'inscrits ne peut pas être négatif")
    private Integer nombreInscrits;

    @NotNull(message = "L'ID du keynote est obligatoire")
    private Long keynoteId;
}