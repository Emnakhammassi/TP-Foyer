package tn.esprit.tpfoyer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entities.Foyer;
import tn.esprit.tpfoyer.entities.Universite;
import tn.esprit.tpfoyer.repositories.IBlocRepository;
import tn.esprit.tpfoyer.repositories.IFoyerRepository;
import tn.esprit.tpfoyer.repositories.IUniversiteRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoyerServiceImpl implements IFoyerServices {
    private final IFoyerRepository foyerRepository;
    private final IUniversiteRepository universiteRepository;
    private final IBlocRepository blocRepository;

    @Override
    public List<Foyer> retrieveAllFoyers() {
        // Retourne la liste de tous les foyers
        return foyerRepository.findAll();
    }

    @Override
    public Foyer addFoyer(Foyer f) {
        // Ajoute un foyer dans la base de données
        return foyerRepository.save(f);
    }

    @Override
    public Foyer updateFoyer(Long id, Foyer f) {
        // Met à jour un foyer existant. Si les champs sont non null, ils sont mis à jour
        return foyerRepository.findById(id)
                .map(existing -> {
                    if (f.getNomFoyer() != null) existing.setNomFoyer(f.getNomFoyer());
                    if (f.getCapaciteFoyer() != null) existing.setCapaciteFoyer(f.getCapaciteFoyer());
                    return foyerRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Foyer non trouvé"));
    }

    @Override
    public Foyer retrieveFoyer(Long idFoyer) {
        // Récupère un foyer par son identifiant
        return foyerRepository.findById(idFoyer)
                .orElseThrow(() -> new RuntimeException("Foyer non trouvé"));
    }

    @Override
    public void removeFoyer(Long idFoyer) {
        // Supprime un foyer de la base de données
        foyerRepository.deleteById(idFoyer);
    }

    @Override
    public Foyer ajouterFoyerEtAffecterAUniversite(Foyer foyer, long idUniversite) {
        // Si la liste des blocs est null, on l'initialise
        if (foyer.getBlocs() == null) {
            foyer.setBlocs(new ArrayList<>());
        }

        // On trouve l'université par son identifiant
        Universite u = universiteRepository.findById(idUniversite)
                .orElseThrow(() -> new RuntimeException("Université non trouvée"));

        // On associe l'université au foyer et on le sauvegarde
        foyer.setUniversite(u);
        Foyer savedFoyer = foyerRepository.save(foyer);

        // On associe chaque bloc au foyer et on les sauvegarde
        foyer.getBlocs().forEach(bloc -> {
            bloc.setFoyer(savedFoyer);
            blocRepository.save(bloc);
        });

        // On met à jour l'université avec le foyer associé
        u.setFoyer(savedFoyer);
        universiteRepository.save(u);

        return savedFoyer;
    }
}
