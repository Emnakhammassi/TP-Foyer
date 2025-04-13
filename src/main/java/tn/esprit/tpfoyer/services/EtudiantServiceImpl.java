package tn.esprit.tpfoyer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entities.Etudiant;
import tn.esprit.tpfoyer.repositories.IEtudiantRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EtudiantServiceImpl implements IEtudiantServices {

    // Injection du repository pour accéder aux données des étudiants
    private final IEtudiantRepository etudiantRepository;

    @Override
    public List<Etudiant> retrieveAllEtudiants() {
        // Retourne la liste de tous les étudiants
        return etudiantRepository.findAll();
    }

    @Override
    public List<Etudiant> addEtudiants(List<Etudiant> etudiants) {
        // Enregistre une liste d'étudiants dans la base
        return etudiantRepository.saveAll(etudiants);
    }

    @Override
    public Etudiant updateEtudiant(Etudiant e) {
        // Met à jour les informations d’un étudiant
        return etudiantRepository.save(e);
    }

    @Override
    public Etudiant retrieveEtudiant(long idEtudiant) {
        // Retourne un étudiant par son ID, ou null s’il n’existe pas
        return etudiantRepository.findById(idEtudiant).orElse(null);
    }

    @Override
    public void removeEtudiant(long idEtudiant) {
        // Supprime un étudiant par son ID
        etudiantRepository.deleteById(idEtudiant);
    }
}
