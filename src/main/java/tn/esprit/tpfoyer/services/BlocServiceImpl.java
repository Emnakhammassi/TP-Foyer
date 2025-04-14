package tn.esprit.tpfoyer.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entities.Bloc;
import tn.esprit.tpfoyer.entities.Chambre;
import tn.esprit.tpfoyer.entities.TypeChambre;
import tn.esprit.tpfoyer.repositories.IBlocRepository;
import tn.esprit.tpfoyer.repositories.IChambreRepository;
import tn.esprit.tpfoyer.repositories.IFoyerRepository;

import java.util.ArrayList;
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
//
//    @Override
//    public Bloc affecterChambresABloc(List<Long> numChambres, long idBloc) {
//        // Récupérer le bloc cible
//        Bloc bloc = blocRepository.findById(idBloc)
//                .orElseThrow(() -> new RuntimeException("Bloc non trouvé"));
//
//        // Récupérer les chambres avec leurs numéros
//        List<Chambre> chambres = chambreRepository.findAllByNumeroChambreIn(numChambres);
//
//        // Vérifie si toutes les chambres demandées existent
//        if (chambres.size() != numChambres.size()) {
//            throw new RuntimeException("Certaines chambres n'existent pas");
//        }
//
//        // Affecte le bloc aux chambres
//        for (Chambre chambre : chambres) {
//            chambre.setBloc(bloc);
//        }
//
//        // Sauvegarde les chambres modifiées
//        chambreRepository.saveAll(chambres);
//
//        return bloc;
//    }


    @Override
   public Bloc affecterChambresABloc(List<Long> numChambres, long idBloc) {
        // 1. Vérifier que le bloc existe
        Bloc bloc = blocRepository.findById(idBloc)
                .orElseThrow(() -> new EntityNotFoundException("Bloc non trouvé avec l'ID: " + idBloc));

        // 2. Vérifier que la liste des chambres n'est pas vide
        if (numChambres == null || numChambres.isEmpty()) {
            throw new IllegalArgumentException("La liste des numéros de chambre ne peut pas être vide");
        }

        // 3. Récupérer les chambres existantes
        List<Chambre> chambres = chambreRepository.findByNumeroChambreIn(numChambres);

        // 4. Vérifier que toutes les chambres existent
        if (chambres.size() != numChambres.size()) {
            List<Long> chambresNonTrouvees = new ArrayList<>(numChambres);
            chambres.forEach(c -> chambresNonTrouvees.remove(c.getNumeroChambre()));
            throw new EntityNotFoundException("Chambres non trouvées: " + chambresNonTrouvees);
        }

        // 5. Affecter le bloc aux chambres
        chambres.forEach(chambre -> {
            // Vérifier si la chambre n'est pas déjà affectée à un autre bloc
            if (chambre.getBloc() != null && !chambre.getBloc().getIdBloc().equals(idBloc)) {
                throw new IllegalStateException(
                        "La chambre " + chambre.getNumeroChambre() + " est déjà affectée au bloc " +
                                chambre.getBloc().getNomBloc()
                );
            }
            chambre.setBloc(bloc);
        });

        // 6. Mettre à jour la capacité du bloc si nécessaire
        long nouvelleCapacite = chambres.stream()
                .mapToLong(c -> c.getTypeC() == TypeChambre.SIMPLE ? 1 :
                        c.getTypeC() == TypeChambre.DOUBLE ? 2 : 3)
                .sum();

        bloc.setCapaciteBloc(bloc.getCapaciteBloc() + nouvelleCapacite);

        // 7. Sauvegarder
        chambreRepository.saveAll(chambres);
        return blocRepository.save(bloc);
    }
}
