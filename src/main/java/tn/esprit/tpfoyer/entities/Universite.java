package tn.esprit.tpfoyer.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // Indique que cette classe est une entité JPA (sera une table dans la BD)
@NoArgsConstructor // Génère un constructeur sans arguments
@AllArgsConstructor // Génère un constructeur avec tous les arguments
@Getter // Génère les getters pour tous les champs
@Setter // Génère les setters pour tous les champs
public class Universite {

    @Id // Clé primaire de l'entité
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrémentation de l'ID
    private Long idUniversite;

    private String nomUniversite; // Nom de l'université

    private String adresse; // Adresse de l'université

    @OneToOne(mappedBy = "universite") // Relation 1-1 inverse avec l'entité Foyer
    @JsonIgnore // Évite les boucles infinies lors de la sérialisation JSON
    private Foyer foyer; // Chaque université a un seul foyer associé
}
