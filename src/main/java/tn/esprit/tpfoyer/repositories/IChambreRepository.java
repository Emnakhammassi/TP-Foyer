package tn.esprit.tpfoyer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.tpfoyer.entities.Chambre;
import tn.esprit.tpfoyer.entities.Reservation;
import tn.esprit.tpfoyer.entities.TypeChambre;
import org.springframework.data.repository.query.Param;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface IChambreRepository extends JpaRepository<Chambre, Long> {
    List<Chambre> findByBlocIdBloc(Long idBloc);

    @Query("SELECT c FROM Chambre c WHERE c.bloc.idBloc = :idBloc AND c.typeC = :type")
    List<Chambre> findByBlocAndType(@Param("idBloc") Long idBloc, @Param("type") TypeChambre type);

    @Query("SELECT c FROM Chambre c WHERE c.bloc.foyer.universite.nomUniversite = :nomUniversite")
    List<Chambre> findByUniversite(@Param("nomUniversite") String nomUniversite);

    List<Chambre> findByTypeC(TypeChambre type);
    // Méthode pour trouver les chambres non réservées
    @Query("SELECT c FROM Chambre c WHERE c.reservations IS EMPTY")
    List<Chambre> findChambresNonReservees();

    // Alternative avec derived query
    List<Chambre> findByReservationsEmpty();

    List<Chambre> findAllByNumeroChambreIn(List<Long> numChambre);

    @Query("SELECT c FROM Chambre c JOIN c.reservations r WHERE r = :reservation")
    Optional<Chambre> findByReservationsContains(@Param("reservation") Reservation reservation);

    @Query("SELECT c FROM Chambre c JOIN c.reservations r WHERE r.idReservation = :idReservation")
    Optional<Chambre> findByReservationId(@Param("idReservation") Long idReservation);

    // Requête dérivée - Spring génère automatiquement la requête
    List<Chambre> findByBloc_Foyer_Universite_NomUniversite(String nomUniversite);

    // Alternative avec JPQL explicite
    @Query("SELECT c FROM Chambre c JOIN c.bloc b JOIN b.foyer f JOIN f.universite u WHERE u.nomUniversite = :nomUniversite")
    List<Chambre> findChambresByUniversiteNom(@Param("nomUniversite") String nomUniversite);

    // Solution 1: Utilisation des keywords de Spring Data JPA
    List<Chambre> findByBloc_IdBlocAndTypeC(long idBloc, TypeChambre typeC);

    // Solution 2: Utilisation de JPQL
    @Query("SELECT c FROM Chambre c WHERE c.bloc.idBloc = :idBloc AND c.typeC = :typeC")
    List<Chambre> findByBlocAndTypeUsingJPQL(@Param("idBloc") long idBloc, @Param("typeC") TypeChambre typeC);


    @Query("SELECT c FROM Chambre c " +
            "JOIN c.bloc b " +
            "JOIN b.foyer f " +
            "JOIN f.universite u " +
            "WHERE u.nomUniversite = :nomUniversite " +
            "AND c.typeC = :type " +
            "AND NOT EXISTS (" +
            "   SELECT r FROM Reservation r " +
            "   JOIN Chambre rc ON r MEMBER OF c.reservations  " +
            "   WHERE rc = c " +
            "   AND r.anneeUniversitaire = :annee " +
            "   AND r.estValide = true" +
            ")")
    List<Chambre> findChambresNonReservees(
            @Param("nomUniversite") String nomUniversite,
            @Param("type") TypeChambre type,
            @Param("annee") Date anneeUniversitaire);


    List<Chambre> findByNumeroChambreIn(List<Long> numChambres);
//    boolean existsByNumeroChambreAndBlocId(Long numeroChambre, Long idBloc);


}