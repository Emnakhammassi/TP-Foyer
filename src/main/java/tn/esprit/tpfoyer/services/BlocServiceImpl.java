package tn.esprit.tpfoyer.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entities.Bloc;
import tn.esprit.tpfoyer.entities.Chambre;
import tn.esprit.tpfoyer.entities.TypeChambre;
import tn.esprit.tpfoyer.repositories.IBlocRepository;
import tn.esprit.tpfoyer.repositories.IChambreReposirtory;

import java.util.List;

@Service
@AllArgsConstructor
public class BlocServiceImpl implements IBlocServices {
    private final IChambreReposirtory chambreRepository;
    IBlocRepository iBlocRepository;

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
    // ✅ Ajout du constructeur pour injecter la dépendance
    @Autowired
    public BlocServiceImpl(IChambreReposirtory chambreRepository) {
        this.chambreRepository = chambreRepository;
    }

    /**
     * Récupérer les chambres non réservées d'un foyer d'une université donnée et de type spécifique.
     */
    public List<Chambre> getChambresNonReserveParNomUniversiteEtTypeChambre(String nomUniversite, TypeChambre type) {
        return chambreRepository.findChambresNonReservees(nomUniversite, type);
    }
}