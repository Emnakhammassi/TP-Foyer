package tn.esprit.tpfoyer.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.tpfoyer.entities.*;
import java.util.List;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r " +
            "WHERE FUNCTION('YEAR', r.anneeUniversitaire) = :anneeUniversitaire " +
            "AND r.chambre.bloc.foyer.universite.nomUniversite = :nomUniversite")
    List<Reservation> findReservationsByAnneeAndUniversite(
            @Param("anneeUniversitaire") int anneeUniversitaire,
            @Param("nomUniversite") String nomUniversite);


}


