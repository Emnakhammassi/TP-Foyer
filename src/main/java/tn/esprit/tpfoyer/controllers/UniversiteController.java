package tn.esprit.tpfoyer.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.entities.Universite;
import tn.esprit.tpfoyer.services.IUniversiteServices;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/universites") // Utilisation du pluriel pour la ressource
@RequiredArgsConstructor
public class UniversiteController {
    private final IUniversiteServices universiteServices;

    // Récupérer toutes les universités
    @GetMapping("/all")
    public List<Universite> getAllUniversites() {
        return universiteServices.retrieveAllUniversities();
    }

    // Ajouter une nouvelle université
    @PostMapping("/add")
    public Universite addUniversite(@RequestBody Universite universite) {
        return universiteServices.addUniversite(universite);
    }

    // Mettre à jour une université existante
    @PutMapping("/update/{id}")
    public Universite updateUniversite(@PathVariable Long id, @RequestBody Universite universite) {
        return universiteServices.updateUniversite(id, universite);
    }

    // Récupérer une université par son ID
    @GetMapping("/{id}")
    public Universite getUniversite(@PathVariable Long id) {
        return universiteServices.retrieveUniversite(id);
    }

    // Supprimer une université par son ID
    @DeleteMapping("/delete/{id}")
    public void deleteUniversite(@PathVariable Long id) {
        universiteServices.removeUniversite(id);
    }

    // Affecter un foyer à une université par l'ID du foyer et le nom de l'université
    @PutMapping("/affecterFoyer/{idFoyer}/{nomUniversite}")
    public ResponseEntity<Universite> affecterFoyerAUniversite(
            @PathVariable long idFoyer, @PathVariable String nomUniversite) {
        Universite universite = universiteServices.affecterFoyerAUniversite(idFoyer, nomUniversite);
        return universite != null ? ResponseEntity.ok(universite) : ResponseEntity.notFound().build();
    }

    // Désaffecter un foyer d'une université par l'ID de l'université
    @PutMapping("/desaffecterFoyer/{idUniversite}")
    public ResponseEntity<Universite> desaffecterFoyer(@PathVariable long idUniversite) {
        Universite universite = universiteServices.desaffecterFoyerAUniversite(idUniversite);
        return universite != null ? ResponseEntity.ok(universite) : ResponseEntity.notFound().build();
    }
}
