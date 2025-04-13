package tn.esprit.tpfoyer.services;

import tn.esprit.tpfoyer.entities.Foyer;
import java.util.List;

public interface IFoyerServices {
    List<Foyer> retrieveAllFoyers();
    Foyer addFoyer(Foyer f);
    Foyer updateFoyer(Long id, Foyer f);
    Foyer retrieveFoyer(Long idFoyer);
    void removeFoyer(Long idFoyer);
    //Ajoute un foyer et l'affecte à une université.
    Foyer ajouterFoyerEtAffecterAUniversite(Foyer foyer, long idUniversite);
}
