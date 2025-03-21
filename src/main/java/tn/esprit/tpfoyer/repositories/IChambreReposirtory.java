package tn.esprit.tpfoyer.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tpfoyer.entities.Chambre;
import tn.esprit.tpfoyer.entities.TypeChambre;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IChambreReposirtory extends CrudRepository<Chambre, Long> {

    Chambre findByNumeroChambre(long num);
    List<Chambre> findByNumeroChambreIn(List<Long> numeroChambres);
    Long countReservationsByIdChambreAndReservationsAnneeUniversitaireBetween(long chambreId, LocalDate dateDebutAU, LocalDate dateFinAU);


}
