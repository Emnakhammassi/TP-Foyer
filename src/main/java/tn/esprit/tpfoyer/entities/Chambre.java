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
public class Chambre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idChambre;
    private Long numeroChambre;

    @Enumerated(EnumType.STRING)
    private TypeChambre typeC;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnore
    private Bloc bloc;

    @OneToMany
    @JoinColumn(name = "chambre_id") // Ceci crée la colonne FK dans Reservation
    @JsonIgnore
    private List<Reservation> reservations ;
}
