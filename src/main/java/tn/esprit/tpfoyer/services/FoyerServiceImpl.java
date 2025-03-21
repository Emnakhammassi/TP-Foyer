package tn.esprit.tpfoyer.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entities.Bloc;
import tn.esprit.tpfoyer.entities.Foyer;
import tn.esprit.tpfoyer.entities.Universite;
import tn.esprit.tpfoyer.repositories.IBlocRepository;
import tn.esprit.tpfoyer.repositories.IChambreReposirtory;
import tn.esprit.tpfoyer.repositories.IFoyerRepository;
import tn.esprit.tpfoyer.repositories.IUniversiteRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FoyerServiceImpl implements IFoyerServices {

    @Autowired
    IFoyerRepository foyerRepository;
    @Autowired
    IUniversiteRepository universiteRepository;
    @Autowired
    IBlocRepository blocRepository;
    @Autowired
    IChambreReposirtory chambreReposirtory;
    @Override
    public Foyer findById(long id) {
        return foyerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Foyer> findAll() {
        return (List<Foyer>) foyerRepository.findAll();
    }

    @Override
    public Foyer save(Foyer foyer) {
        return foyerRepository.save(foyer);
    }

    @Override
    public void delete(Long id) {
        foyerRepository.deleteById(id);
    }

    @Override
    public Foyer getNomCapacite(String nom, Long capacite) {
        return foyerRepository.findByNomFoyerAndCapaciteFoyer(nom, capacite);
    }

    @Override
    public List<Foyer> retrieveAllFoyers() {
        return List.of();
    }

    @Override
    public Foyer addFoyer(Foyer f)
    {
        return foyerRepository.save(f);
    }


    @Override
    public Foyer updateFoyer(Foyer f) {
        if (foyerRepository.existsById(f.getIdFoyer())) {
            return foyerRepository.save(f);
        }
        return null;
    }

    @Override
    public Foyer retrieveFoyer(long idFoyer) {
        return foyerRepository.findById(idFoyer).orElse(null);
    }

    @Override
    public void removeFoyer(long idFoyer) {

        foyerRepository.deleteById(idFoyer);
    }

    @Override
    public Foyer ajouterFoyerEtAffecterAUniversite(Foyer foyer, long idUniversite) {
        // Récupérer la liste des blocs avant de faire l'ajout
        List<Bloc> blocs = foyer.getBlocs();

        // Si la liste des blocs est null, l'initialiser avec une liste vide
        if (blocs == null) {
            blocs = new ArrayList<>();
        }

        // Sauvegarder le foyer (enregistrer ou mettre à jour le foyer dans la base de données)
        Foyer f = foyerRepository.save(foyer);

        // Récupérer l'université à partir de l'ID
        Universite u = universiteRepository.findById(idUniversite).orElse(null);
        if (u == null) {
            // Si l'université n'existe pas, on peut choisir de retourner null ou de lancer une exception
            // pour indiquer que l'université n'a pas été trouvée
            throw new RuntimeException("Université non trouvée avec l'ID: " + idUniversite);
        }

        // Affecter chaque bloc au foyer
        for (Bloc bloc : blocs) {
            bloc.setFoyer(f);
            blocRepository.save(bloc); // Sauvegarder chaque bloc
        }

        // Associer le foyer à l'université
        u.setFoyer(f);

        // Sauvegarder l'université avec la relation de foyer mise à jour
        return universiteRepository.save(u).getFoyer();
    }

}