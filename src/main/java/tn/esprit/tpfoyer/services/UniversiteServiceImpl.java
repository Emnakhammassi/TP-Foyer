package tn.esprit.tpfoyer.services;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entities.*;
import tn.esprit.tpfoyer.repositories.IFoyerRepository;
import tn.esprit.tpfoyer.repositories.IUniversiteRepository;
import java.util.List;


import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UniversiteServiceImpl implements IUniversiteServices {
    IUniversiteRepository universiteRepository;
    IFoyerRepository foyerRepository;

    @Override
    public List<Universite> retrieveAllUniversities() {
        return universiteRepository.findAll();
    }

    @Override
    public Universite addUniversite(Universite u) {
        return universiteRepository.save(u);
    }

    @Override
    public Universite updateUniversite(Universite u) {
        if (universiteRepository.existsById(u.getIdUniversite())) {
            return universiteRepository.save(u);
        }
        return null;
    }

    @Override
    public Universite retrieveUniversite(long idUniversite) {

        return universiteRepository.findById(idUniversite).orElse(null);
    }

    ///
    @Override
    public Foyer affecterFoyerAUniversite(long idFoyer, String nomUniversite) {
        Foyer foyer = foyerRepository.findById(idFoyer).orElse(null);
        if (foyer == null) {
            throw new RuntimeException("Foyer introuvable !");
        }
        // Récupérer l'université par son nom
        Universite universite = universiteRepository.findByNomUniversite(nomUniversite);
        if (universite == null) {
            throw new RuntimeException("Université introuvable !");
        }

        // Affecter le foyer à l'université
        foyer.setUniversite(universite);
        return foyerRepository.save(foyer);
    }


    @Transactional
    public Universite desaffecterFoyerAUniversite(long idUniversite) {

        // Récupérer l'université par son ID
        Universite universite = universiteRepository.findById(idUniversite).orElse(null);

        // Vérifier si l'université a un foyer associé
        if (universite.getFoyer() == null) {
            throw new RuntimeException("Cette université n'a pas de foyer associé !");
        }

        // Récupérer le foyer associé
        Foyer foyer = universite.getFoyer();

        // Désaffecter l'université du foyer
        foyer.setUniversite(null);

        // Sauvegarder la modification du foyer
        foyerRepository.save(foyer);

        // Mettre à jour l'université en supprimant l'association avec le foyer
        universite.setFoyer(null);
        return universiteRepository.save(universite);
    }
}