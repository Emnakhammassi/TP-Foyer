package tn.esprit.tpfoyer.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tpfoyer.entities.Chambre;
import tn.esprit.tpfoyer.entities.TypeChambre;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface IChambreReposirtory extends CrudRepository<Chambre, Long> {

    @Query("SELECT c FROM Chambre c " +
            "WHERE c.bloc.foyer.universite.nomUniversite = :nomUniversite " +
            "AND c.typeC = :type " +
            "AND c NOT IN (SELECT r.chambre FROM Reservation r WHERE r.estValide = true)")
    List<Chambre> findChambresNonReservees(String nomUniversite, TypeChambre type);

    // Solution 1 : Requête JPQL
    @Query("SELECT c FROM Chambre c WHERE c.bloc.idBloc = :idBloc AND c.type = :typeC")
    List<Chambre> findChambresParBlocEtTypeJPQL(
            @Param("idBloc") Long idBloc,
            @Param("typeC") TypeChambre typeC
    );

    // Solution 2 : Dérivation par Keywords
    List<Chambre> findByBlocIdBlocAndType(Long idBloc, TypeChambre type);
}
