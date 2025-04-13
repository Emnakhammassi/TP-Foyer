package tn.esprit.tpfoyer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tpfoyer.entities.Foyer;

@Repository
public interface IFoyerRepository extends JpaRepository<Foyer, Long> {

    //  Récupère un foyer par son nom et sa capacité
     Foyer findByNomFoyerAndCapaciteFoyer(String nomFoyer, Long capaciteFoyer);

    //  Récupère un foyer par son nom
    Foyer findByNomFoyer(String nom);


}