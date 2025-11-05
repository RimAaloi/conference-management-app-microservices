package ma.enset.keynoteservice.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateKeynoteRequest {

    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    private String nom;

    @Size(min = 2, max = 50, message = "Le prénom doit contenir entre 2 et 50 caractères")
    private String prenom;

    @Email(message = "L'email doit être valide")
    private String email;

    @Size(min = 2, max = 100, message = "La fonction doit contenir entre 2 et 100 caractères")
    private String fonction;
}
