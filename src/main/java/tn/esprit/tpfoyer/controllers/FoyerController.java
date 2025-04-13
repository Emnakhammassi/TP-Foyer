package tn.esprit.tpfoyer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.entities.Foyer;
import tn.esprit.tpfoyer.services.IFoyerServices;

import java.util.List;

@RestController
@RequestMapping("/foyers")
@RequiredArgsConstructor
public class FoyerController {

    private final IFoyerServices foyerService;

    // Récupérer tous les foyers
    @GetMapping
    public List<Foyer> getAllFoyers() {
        return foyerService.retrieveAllFoyers();
    }

    // Ajouter un nouveau foyer
    @PostMapping
    public Foyer addFoyer(@RequestBody Foyer foyer) {
        return foyerService.addFoyer(foyer);
    }

    // Mettre à jour un foyer existant
    @PutMapping("/{id}")
    public Foyer updateFoyer(@PathVariable Long id, @RequestBody Foyer foyer) {
        return foyerService.updateFoyer(id, foyer);
    }

    // Récupérer un foyer par ID
    @GetMapping("/{id}")
    public Foyer getFoyer(@PathVariable Long id) {
        return foyerService.retrieveFoyer(id);
    }

    // Supprimer un foyer par ID
    @DeleteMapping("/{id}")
    public void deleteFoyer(@PathVariable Long id) {
        foyerService.removeFoyer(id);
    }

    // Ajouter un foyer et l'affecter à une université spécifique
    @PostMapping("/ajouter-et-affecter/{idUniversite}")
    public ResponseEntity<Foyer> ajouterFoyerEtAffecterAUniversite(
            @RequestBody Foyer foyer,
            @PathVariable long idUniversite) {

        Foyer createdFoyer = foyerService.ajouterFoyerEtAffecterAUniversite(foyer, idUniversite);

        return new ResponseEntity<>(createdFoyer, HttpStatus.CREATED);
    }
}
