package tn.esprit.tpfoyer.services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entities.Chambre;
import tn.esprit.tpfoyer.entities.TypeChambre;
import tn.esprit.tpfoyer.repositories.IChambreReposirtory;

import java.util.List;

@Service

public class ChambreServiceImpl implements IChambreServices {
    @Autowired
    public ChambreServiceImpl(IChambreReposirtory chambreRepository) {
        this.chambreRepository = chambreRepository;
    }

    private final IChambreReposirtory chambreRepository;

    @Override
    public List<Chambre> retrieveAllChambres() {
        return (List<Chambre>) chambreRepository.findAll();
    }

    @Override
    public Chambre addChambre(Chambre c) {
        return chambreRepository.save(c);
    }

    @Override
    public Chambre updateChambre(Chambre c) {
        if (chambreRepository.existsById(c.getIdChambre())) {
            return chambreRepository.save(c);
        }
        return null;
    }

    @Override
    public Chambre retrieveChambre(long idChambre) {
        return chambreRepository.findById(idChambre).orElse(null);
    }





}
