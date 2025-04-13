package tn.esprit.tpfoyer.services;

import tn.esprit.tpfoyer.entities.Bloc;
import java.util.List;

public interface IBlocServices {

    // Récupère tous les blocs
    List<Bloc> retrieveAllBlocs();

    // Ajoute un nouveau bloc
    Bloc addBloc(Bloc bloc);

    // Met à jour un bloc existant
    Bloc updateBloc(Long idBloc, Bloc blocDetails);

    // Récupère un bloc par ID
    Bloc retrieveBloc(Long idBloc);

    // Supprime un bloc par ID
    void removeBloc(Long idBloc);

    // Affecte une liste de chambres à un bloc
    Bloc affecterChambresABloc(List<Long> numChambres, long idBloc);
}
