package tn.esprit.tpfoyer.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tpfoyer.entities.Universite;

import java.util.List;

@Repository
public interface IUniversiteRepository extends JpaRepository<Universite, Long> {

    // Rechercher des universités dont le nom contient un mot-clé
    List<Universite> findByNomUniversite(String keyword);

}