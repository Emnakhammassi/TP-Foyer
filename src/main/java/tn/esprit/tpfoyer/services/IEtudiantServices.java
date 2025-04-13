package tn.esprit.tpfoyer.services;

import tn.esprit.tpfoyer.entities.Etudiant;
import java.util.List;

public interface IEtudiantServices {

    // Récupère la liste de tous les étudiants
    List<Etudiant> retrieveAllEtudiants();

    // Ajoute une liste d'étudiants
    List<Etudiant> addEtudiants(List<Etudiant> etudiants);

    // Met à jour un étudiant
    Etudiant updateEtudiant(Etudiant e);

    // Récupère un étudiant par son ID
    Etudiant retrieveEtudiant(long idEtudiant);

    // Supprime un étudiant par son ID
    void removeEtudiant(long idEtudiant);
}
