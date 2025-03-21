package tn.esprit.tpfoyer.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import tn.esprit.tpfoyer.entities.Bloc;
import tn.esprit.tpfoyer.entities.Chambre;
import tn.esprit.tpfoyer.entities.Foyer;
import tn.esprit.tpfoyer.repositories.IBlocRepository;
import tn.esprit.tpfoyer.repositories.IChambreReposirtory;
import tn.esprit.tpfoyer.repositories.IFoyerRepository;


import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BlocServiceImpl implements IBlocServices {
    @Autowired
    private final IChambreReposirtory chambreRepository;
    @Autowired
    IBlocRepository iBlocRepository;
    @Autowired
    IBlocRepository blocRepository;
    @Autowired
    IFoyerRepository foyerRepository;

    @Override
    public List<Bloc> retrieveBlocs() {
        return iBlocRepository.findAll();
    }

    @Override
    public Bloc updateBloc(Bloc bloc) {
        if (iBlocRepository.existsById(bloc.getIdBloc())) {
            return iBlocRepository.save(bloc);
        }
        return null;
    }

    @Override
    public Bloc addBloc(Bloc bloc) {
        return iBlocRepository.save(bloc);
    }

    @Override
    public Bloc retrieveBloc(long idBloc) {
        return iBlocRepository.findById(idBloc).orElse(null);
    }

    @Override
    public void removeBloc(long idBloc) {
        iBlocRepository.deleteById(idBloc);

    }



    @Override
    public Bloc affecterBlocAFoyer(String nomBloc, String nomFoyer) {
        Bloc b = blocRepository.findByNomBloc(nomBloc); //Parent
        Foyer f = foyerRepository.findByNomFoyer(nomFoyer); //Child
        //On affecte le child au parent
        b.setFoyer(f);
        return blocRepository.save(b);
    }

    @Override
    @Transactional
    public Bloc affecterChambresABloc(List<Long> numChambres, long idBloc) {
        // 1️⃣ Vérifier que le Bloc existe
        Bloc bloc = iBlocRepository.findById(idBloc).orElse(null);

        // 2️⃣ Vérifier que les Chambres existent
        List<Chambre> chambres = new ArrayList<>();
        for (Long numero : numChambres) {
            Chambre chambre = chambreRepository.findByNumeroChambre(numero);
            if (chambre == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chambre avec numéro " + numero + " non trouvée");
            }
            chambres.add(chambre);
        }

        // 3️⃣ Affecter les Chambres au Bloc
        for (Chambre chambre : chambres) {
            chambre.setBloc(bloc);
        }

        // 4️⃣ Sauvegarder toutes les chambres mises à jour
        chambreRepository.saveAll(chambres);

        return bloc;
    }
//    public Bloc affecterChambresABloc(List<Long> numChambres, long idBloc) {
//        // 1. Récupérer le Bloc par son ID
//        Bloc b = iBlocRepository.findById(idBloc).orElseThrow(() -> new RuntimeException("Bloc non trouvé"));
//
//        // 2. Récupérer les Chambres par leurs numéros
//        List<Chambre> chambres = new ArrayList<>();
//        for (Long numChambre : numChambres) {
//            Chambre chambre = chambreRepository.findByNumeroChambre(numChambre);
//            if (chambre != null) {
//                chambres.add(chambre);
//            } else {
//                throw new RuntimeException("Chambre avec numéro " + numChambre + " non trouvée");
//            }
//        }

        // 3. Affecter chaque Chambre au Bloc
//        for (Chambre cha : chambres) {
//            cha.setBloc(b);  // Affecter le Bloc à la Chambre
//            chambreRepository.save(cha);  // Sauvegarder la Chambre avec le Bloc affecté
//        }
//
//        // 4. Retourner le Bloc mis à jour
//        return b;
//    }

    // Ajout du constructeur pour injecter la dépendance
    @Autowired
    public BlocServiceImpl(IChambreReposirtory chambreRepository) {
        this.chambreRepository = chambreRepository;
    }


}