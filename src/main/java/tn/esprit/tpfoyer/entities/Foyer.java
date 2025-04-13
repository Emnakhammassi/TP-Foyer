package tn.esprit.tpfoyer.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Foyer {
    @Id // Clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Génération automatique de l'ID
    private Long idFoyer;

    private String nomFoyer; // Nom du foyer

    private Long capaciteFoyer; // Capacité totale du foyer (en nombre de places)

    @OneToOne // Relation 1-1 directe avec Université
    private Universite universite; // Chaque foyer est rattaché à une seule université

    @OneToMany(mappedBy = "foyer") // Relation 1-N avec Bloc, (Bloc contient une référence vers Foyer)
    private List<Bloc> blocs; // Liste des blocs appartenant à ce foyer
}
