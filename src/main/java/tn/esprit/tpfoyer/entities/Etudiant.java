package tn.esprit.tpfoyer.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Etudiant {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long idEtudiant;
        private String nomEt;
        private String prenomEt;
        private Long cin;
        private String ecole;
        private Date dateNaissance;

        @ManyToMany(mappedBy = "etudiants")
        @JsonIgnore
        private List<Reservation> reservations ;

        public Long getIdEtudiant() {
                return idEtudiant;
        }


}