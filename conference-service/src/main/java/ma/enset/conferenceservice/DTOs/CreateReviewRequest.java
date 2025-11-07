package ma.enset.conferenceservice.DTOs;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewRequest {

    @NotBlank(message = "Le texte de la review est obligatoire")
    @Size(min = 10, max = 1000, message = "Le texte doit contenir entre 10 et 1000 caractères")
    private String texte;

    @NotNull(message = "La note est obligatoire")
    @Min(value = 1, message = "La note minimum est 1")
    @Max(value = 5, message = "La note maximum est 5")
    private Integer note;

    @NotNull(message = "L'ID de la conférence est obligatoire")
    private Long conferenceId;
}