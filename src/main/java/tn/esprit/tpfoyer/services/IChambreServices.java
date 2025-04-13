package tn.esprit.tpfoyer.services;

import tn.esprit.tpfoyer.entities.Chambre;
import tn.esprit.tpfoyer.entities.TypeChambre;

import java.util.List;

public interface IChambreServices {

    // Ajouter une chambre
    Chambre addChambre(Chambre c);

    // Mettre à jour une chambre par son ID
    Chambre updateChambre(Long idChambre, Chambre c);

    // Récupérer une chambre par son ID
    Chambre retrieveChambre(Long idChambre);

    // Récupérer toutes les chambres
    List<Chambre> retrieveAllChambres();

    // Récupérer les chambres d’un bloc spécifique
    List<Chambre> findByBlocId(Long idBloc);

    // Récupérer les chambres par type (SIMPLE, DOUBLE, TRIPLE)
    List<Chambre> findByType(TypeChambre type);

    // Récupérer les chambres par nom d’université
    List<Chambre> findByUniversite(String nomUniversite);

    // Récupérer les chambres qui ne sont pas encore réservées
    List<Chambre> getChambresNonReservees();

    // Récupérer les chambres par nom d’université
    List<Chambre> getChambresParNomUniversite(String nomUniversite);

    // Récupérer les chambres selon le bloc et le type
    List<Chambre> getChambresParBlocEtType(Long idBloc, TypeChambre typeC);

    // Récupérer les chambres non réservées selon université + type de chambre
    List<Chambre> getChambresNonReserveParNomUniversiteEtTypeChambre(String nomUniversite, TypeChambre type);
}
