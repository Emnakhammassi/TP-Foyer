package tn.esprit.tpfoyer.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Etudiant {
        @Id // Clé primaire
        @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrémentation de l'ID
        private Long idEtudiant;

        private String nomEt; // Nom de l'étudiant
        private String prenomEt; // Prénom de l'étudiant
        private Long cin; // Numéro CIN de l'étudiant
        private String ecole; // École fréquentée par l'étudiant
        private Date dateNaissance; // Date de naissance

        @ManyToMany(mappedBy = "etudiants") // Relation ManyToMany avec Reservation (côté inverse)
        @JsonIgnore // Pour éviter les boucles infinies lors de la sérialisation JSON
        private List<Reservation> reservations; // Liste des réservations de l'étudiant
}