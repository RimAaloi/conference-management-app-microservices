package ma.enset.keynoteservice.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeynoteResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String fonction;
}