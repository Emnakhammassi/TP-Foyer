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
    IFoyerRepository foyerRepository; // Ajout du repository pour Foyer


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
    @Transactional
    public Universite affecterFoyerAUniversite(long idFoyer, String nomUniversite) {
        // Récupérer le foyer par son ID
        Foyer foyer = foyerRepository.findById(idFoyer)
                .orElseThrow(() -> new RuntimeException("Foyer non trouvé avec l'ID : " + idFoyer));

        // Récupérer la liste des universités par nom
        List<Universite> universites = universiteRepository.findByNomUniversite(nomUniversite);

        // Vérifier si la liste est vide
        if (universites.isEmpty()) {
            throw new RuntimeException("Aucune université trouvée avec le nom : " + nomUniversite);
        }

        // Prendre la première université de la liste
        Universite universite = universites.get(0);

        // Vérifier si l'université a déjà un foyer associé
        if (universite.getFoyer() != null) {
            throw new RuntimeException("L'université a déjà un foyer associé");
        }

        // Affecter le foyer à l'université
        universite.setFoyer(foyer);
        foyer.setUniversite(universite);

        // Sauvegarder les modifications
        return universiteRepository.save(universite);
    }

    @Override
    public Universite desaffecterFoyerAUniversite(long idUniversite) {
        return null;
    }

}