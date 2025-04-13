package tn.esprit.tpfoyer.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entities.Chambre;
import tn.esprit.tpfoyer.entities.TypeChambre;
import tn.esprit.tpfoyer.entities.Universite;
import tn.esprit.tpfoyer.repositories.IBlocRepository;
import tn.esprit.tpfoyer.repositories.IChambreRepository;
import tn.esprit.tpfoyer.repositories.IUniversiteRepository;


import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChambreServiceImpl implements IChambreServices {

    private final IChambreRepository chambreRepository;
    private final IBlocRepository blocRepository;
    private final IUniversiteRepository universiteRepository;

    // Ajouter une nouvelle chambre
    @Override
    public Chambre addChambre(Chambre c) {
        return chambreRepository.save(c);
    }

    // Mettre à jour une chambre existante
    @Override
    public Chambre updateChambre(Long id, Chambre c) {
        return chambreRepository.findById(id)
                .map(existing -> {
                    if (c.getNumeroChambre() != null) existing.setNumeroChambre(c.getNumeroChambre());
                    if (c.getTypeC() != null) existing.setTypeC(c.getTypeC());
                    if (c.getBloc() != null) existing.setBloc(c.getBloc());
                    return chambreRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Chambre non trouvée"));
    }

    // Récupérer une chambre par ID
    @Override
    public Chambre retrieveChambre(Long id) {
        return chambreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chambre non trouvée"));
    }

    // Récupérer toutes les chambres
    @Override
    public List<Chambre> retrieveAllChambres() {
        return chambreRepository.findAll();
    }

    // Récupérer les chambres d’un bloc donné
    @Override
    public List<Chambre> findByBlocId(Long idBloc) {
        return chambreRepository.findByBlocIdBloc(idBloc);
    }

    // Récupérer les chambres selon le type
    @Override
    public List<Chambre> findByType(TypeChambre type) {
        return chambreRepository.findByTypeC(type);
    }

    // Récupérer les chambres selon le nom d’université
    @Override
    public List<Chambre> findByUniversite(String nomUniversite) {
        return chambreRepository.findByUniversite(nomUniversite);
    }

    // Récupérer toutes les chambres non réservées
    @Override
    public List<Chambre> getChambresNonReservees() {
        return chambreRepository.findChambresNonReservees();
    }

    // Récupérer les chambres selon le nom d’université (avec vérif)
    @Override
    public List<Chambre> getChambresParNomUniversite(String nomUniversite) {
        Universite universite = universiteRepository.findByNomUniversite(nomUniversite);
        if (universite == null) throw new RuntimeException("Université non trouvée");
        return chambreRepository.findByBloc_Foyer_Universite_NomUniversite(nomUniversite);
    }

    // Récupérer les chambres selon le bloc et le type (2 implémentations possibles)
    @Override
    public List<Chambre> getChambresParBlocEtType(Long idBloc, TypeChambre typeC) {
        blocRepository.findById(idBloc)
                .orElseThrow(() -> new EntityNotFoundException("Bloc non trouvé avec l'ID: " + idBloc));

        // Choisir une des deux méthodes selon le besoin
        return chambreRepository.findByBlocAndTypeUsingJPQL(idBloc, typeC);
        // return chambreRepository.findByBloc_IdBlocAndTypeC(idBloc, typeC);
    }

    // Récupérer les chambres non réservées selon nom université + type
    @Override
    public List<Chambre> getChambresNonReserveParNomUniversiteEtTypeChambre(String nomUniversite, TypeChambre type) {
        Universite universite = universiteRepository.findByNomUniversite(nomUniversite);
        if (universite == null) throw new RuntimeException("Université non trouvée");

        Date anneeActuelle = new Date(); // Peut être remplacée par une logique métier
        return chambreRepository.findChambresNonReservees(nomUniversite, type, anneeActuelle);
    }
}
