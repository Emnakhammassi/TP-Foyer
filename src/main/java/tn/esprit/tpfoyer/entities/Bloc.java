package tn.esprit.tpfoyer.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Bloc {

    @Id // Clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID auto-incrémenté
    private Long idBloc;

    private String nomBloc; // Nom du bloc

    private Long capaciteBloc; // Capacité totale du bloc

    @ManyToOne // Plusieurs blocs peuvent appartenir à un seul foyer
    @JsonIgnore // Empêche les boucles infinies en JSON
    private Foyer foyer; // Référence vers le foyer auquel appartient ce bloc

    @OneToMany(mappedBy = "bloc", cascade = CascadeType.REMOVE)// Un bloc peut contenir plusieurs chambres
    @JsonIgnore // Empêche la sérialisation JSON récursive
    private List<Chambre> chambres; // Liste des chambres contenues dans ce bloc

}