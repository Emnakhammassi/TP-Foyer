package tn.esprit.tpfoyer.services;

import tn.esprit.tpfoyer.entities.Universite;
import java.util.List;

public interface IUniversiteServices {

    // Récupère la liste de toutes les universités
    List<Universite> retrieveAllUniversities();

    // Ajoute une nouvelle université
    Universite addUniversite(Universite universite);

    // Met à jour une université existante en fonction de son ID
    Universite updateUniversite(Long idUniversite, Universite universite);

    // Récupère une université spécifique par son ID
    Universite retrieveUniversite(Long idUniversite);

    // Supprime une université par son ID
    void removeUniversite(Long idUniversite);

    // Associe un foyer à une université en fonction de l'ID du foyer et du nom de l'université
    Universite affecterFoyerAUniversite(long idFoyer, String nomUniversite);

    // Désassocie un foyer d'une université en fonction de l'ID de l'université
    Universite desaffecterFoyerAUniversite(long idUniversite);
}
