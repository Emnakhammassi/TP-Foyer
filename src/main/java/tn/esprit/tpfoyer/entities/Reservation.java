package tn.esprit.tpfoyer.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;
    private Date anneeUniversitaire;
    private boolean estValide;
    private String numReservation;

    @ManyToMany
    @JsonIgnore
    private Set<Etudiant> etudiants;

    public long  getIdReservation() {
        return idReservation;

    }


    public void setNumReservation(String numReservation) {
        this.numReservation = numReservation;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiants.add(etudiant);  // Assuming the relation is many-to-many
    }


}