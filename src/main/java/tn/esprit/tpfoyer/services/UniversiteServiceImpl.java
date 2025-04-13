package tn.esprit.tpfoyer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entities.Foyer;
import tn.esprit.tpfoyer.entities.Universite;
import tn.esprit.tpfoyer.repositories.IUniversiteRepository;
import tn.esprit.tpfoyer.repositories.IFoyerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversiteServiceImpl implements IUniversiteServices {

    private final IUniversiteRepository universiteRepository; // Repository pour les opérations sur les universités
    private final IFoyerRepository foyerRepository; // Repository pour les opérations sur les foyers

    @Override
    public List<Universite> retrieveAllUniversities() {
        // Récupère toutes les universités de la base de données
        return universiteRepository.findAll();
    }

    @Override
    public Universite addUniversite(Universite universite) {
        // Ajoute une nouvelle université dans la base de données
        return universiteRepository.save(universite);
    }

    @Override
    public Universite updateUniversite(Long idUniversite, Universite universite) {
        // Récupère l'université existante par son ID
        Universite existingUniversite = universiteRepository.findById(idUniversite).orElse(null);

        if (existingUniversite != null) {
            // Si l'université existe, met à jour ses informations
            existingUniversite.setNomUniversite(universite.getNomUniversite());
            existingUniversite.setAdresse(universite.getAdresse());
            // Mettez à jour d'autres champs si nécessaire
            return universiteRepository.save(existingUniversite);
        }

        return null; // Si l'université n'existe pas, retourne null (ou une exception selon la gestion des erreurs)
    }

    @Override
    public Universite retrieveUniversite(Long idUniversite) {
        // Récupère une université par son ID, si elle existe
        return universiteRepository.findById(idUniversite).orElse(null);
    }

    @Override
    public void removeUniversite(Long idUniversite) {
        // Supprime une université par son ID
        universiteRepository.deleteById(idUniversite);
    }

    @Override
    public Universite affecterFoyerAUniversite(long idFoyer, String nomUniversite) {
        // Récupère l'université par son nom
        Universite universite = universiteRepository.findByNomUniversite(nomUniversite);
        // Récupère le foyer par son ID
        Foyer foyer = foyerRepository.findById(idFoyer).orElse(null);

        if (universite != null && foyer != null) {
            // Si l'université et le foyer existent, associer le foyer à l'université
            foyer.setUniversite(universite);
            foyerRepository.save(foyer); // Sauvegarde du foyer avec la nouvelle association
            return universite;
        }

        return null; // Si l'un des éléments est null, retourne null
    }

    @Override
    public Universite desaffecterFoyerAUniversite(long idUniversite) {
        // Récupère l'université par son ID
        Universite universite = universiteRepository.findById(idUniversite).orElse(null);

        // Vérifie si l'université a un foyer associé
        if (universite == null || universite.getFoyer() == null) {
            throw new RuntimeException("Cette université n'a pas de foyer associé !");
        }

        // Récupère le foyer associé à l'université
        Foyer foyer = universite.getFoyer();

        // Désassocie l'université du foyer
        foyer.setUniversite(null);
        foyerRepository.save(foyer); // Sauvegarde du foyer avec la désassociation

        // Supprime l'association du foyer dans l'université
        universite.setFoyer(null);

        // Met à jour et sauvegarde l'université sans le foyer associé
        return universiteRepository.save(universite);
    }
}
