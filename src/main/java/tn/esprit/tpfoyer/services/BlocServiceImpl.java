package tn.esprit.tpfoyer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entities.Bloc;
import tn.esprit.tpfoyer.entities.Chambre;
import tn.esprit.tpfoyer.repositories.IBlocRepository;
import tn.esprit.tpfoyer.repositories.IChambreRepository;
import tn.esprit.tpfoyer.repositories.IFoyerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlocServiceImpl implements IBlocServices {

    private final IBlocRepository blocRepository;
    private final IChambreRepository chambreRepository;
    private final IFoyerRepository foyerRepository;

    @Override
    public List<Bloc> retrieveAllBlocs() {
        return blocRepository.findAll();
    }

    @Override
    public Bloc addBloc(Bloc bloc) {
        return blocRepository.save(bloc);
    }

    @Override
    public Bloc updateBloc(Long idBloc, Bloc blocDetails) {
        return blocRepository.findById(idBloc)
                .map(existingBloc -> {
                    // Met à jour uniquement les champs nécessaires
                    if (blocDetails.getNomBloc() != null) {
                        existingBloc.setNomBloc(blocDetails.getNomBloc());
                    }
                    if (blocDetails.getCapaciteBloc() != null) {
                        existingBloc.setCapaciteBloc(blocDetails.getCapaciteBloc());
                    }
                    return blocRepository.save(existingBloc);
                })
                .orElseThrow(() -> new RuntimeException("Bloc non trouvé avec l'ID: " + idBloc));
    }

    @Override
    public Bloc retrieveBloc(Long idBloc) {
        return blocRepository.findById(idBloc).orElse(null);
    }

    @Override
    public void removeBloc(Long idBloc) {
        blocRepository.deleteById(idBloc);
    }

    @Override
    public Bloc affecterChambresABloc(List<Long> numChambres, long idBloc) {
        // Récupérer le bloc cible
        Bloc bloc = blocRepository.findById(idBloc)
                .orElseThrow(() -> new RuntimeException("Bloc non trouvé"));

        // Récupérer les chambres avec leurs numéros
        List<Chambre> chambres = chambreRepository.findAllByNumeroChambreIn(numChambres);

        // Vérifie si toutes les chambres demandées existent
        if (chambres.size() != numChambres.size()) {
            throw new RuntimeException("Certaines chambres n'existent pas");
        }

        // Affecte le bloc aux chambres
        for (Chambre chambre : chambres) {
            chambre.setBloc(bloc);
        }

        // Sauvegarde les chambres modifiées
        chambreRepository.saveAll(chambres);

        return bloc;
    }
}
