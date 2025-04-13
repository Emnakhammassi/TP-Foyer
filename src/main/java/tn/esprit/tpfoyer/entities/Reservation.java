package tn.esprit.tpfoyer.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Reservation {

    @Id // Clé primaire de l'entité
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID auto-généré par la base de données
    private Long idReservation;

    private Date anneeUniversitaire; // Année universitaire de la réservation

    private boolean estValide; // Indique si la réservation est toujours valide (true/false)

    private String numReservation; // Code unique de la réservation (ex: chambre-bloc-année)

    @ManyToMany // Une réservation peut concerner plusieurs étudiants (et inversement)
    @JsonIgnore // Évite les problèmes de boucles infinies lors de la conversion JSON
    private Set<Etudiant> etudiants; // Liste des étudiants liés à cette réservation
}

