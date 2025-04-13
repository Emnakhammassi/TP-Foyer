package tn.esprit.tpfoyer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.tpfoyer.entities.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation, Long> {

    // Récupère une réservation par son ID (alternative à findById avec Optional)
    Reservation findByIdReservation(Long idReservation);

    // Trouve une réservation en fonction du CIN de l'étudiant (via la relation ManyToMany)
    Optional<Reservation> findByEtudiantsCin(long cinEtudiant);

    // Trouve une réservation valide (estValide = true) pour un étudiant donné
    Optional<Reservation> findByEtudiantsContainsAndEstValideTrue(Etudiant etudiant);

    // Requête JPQL : Récupère toutes les réservations valides pour une année universitaire donnée
    // et pour une université spécifique (en utilisant les jointures Chambre → Bloc → Foyer → Université)
    @Query("""
           SELECT r FROM Reservation r 
           JOIN Chambre c ON r MEMBER OF c.reservations  
           JOIN c.bloc b  
           JOIN b.foyer f  
           JOIN f.universite u  
           WHERE r.anneeUniversitaire = :annee  
           AND u.nomUniversite = :nomUniversite  
           """)
    List<Reservation> findByAnneeAndUniversite(
            @Param("annee") Date anneeUniversitaire,  // Paramètre pour l'année universitaire
            @Param("nomUniversite") String nomUniversite);  // Paramètre pour le nom de l'université
}
