package tn.esprit.tpfoyer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.entities.Etudiant;
import tn.esprit.tpfoyer.services.IEtudiantServices;

import java.util.List;

@RestController
@RequestMapping("/etudiants") // Convention REST : ressource au pluriel
@RequiredArgsConstructor
public class EtudiantController {

    private final IEtudiantServices etudiantService;

    // Récupérer tous les étudiants
    @GetMapping
    public List<Etudiant> getAllEtudiants() {
        return etudiantService.retrieveAllEtudiants();
    }

    // Ajouter une liste d'étudiants
    @PostMapping
    public List<Etudiant> addEtudiants(@RequestBody List<Etudiant> etudiants) {
        return etudiantService.addEtudiants(etudiants);
    }

    // Mettre à jour un étudiant
    @PutMapping
    public Etudiant updateEtudiant(@RequestBody Etudiant e) {
        return etudiantService.updateEtudiant(e);
    }

    // Récupérer un étudiant par ID
    @GetMapping("/{id}")
    public Etudiant getEtudiant(@PathVariable long id) {
        return etudiantService.retrieveEtudiant(id);
    }

    // Supprimer un étudiant par ID
    @DeleteMapping("/{id}")
    public void deleteEtudiant(@PathVariable long id) {
        etudiantService.removeEtudiant(id);
    }
}
