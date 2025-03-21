package tn.esprit.tpfoyer.controllers;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.entities.Foyer;
import tn.esprit.tpfoyer.entities.Universite;
import tn.esprit.tpfoyer.services.IUniversiteServices;

import org.springframework.http.ResponseEntity;


import java.util.List;

@RestController
@RequestMapping("/universite")
@AllArgsConstructor
public class UniversiteController {
    IUniversiteServices universiteService;


    @GetMapping("/getAllUniversities")
    public List<Universite> retrieveAllUniversities() {
        return universiteService.retrieveAllUniversities();
    }


    @PostMapping("/addUniversite")
    public Universite addUniversite(@RequestBody Universite universite) {
        return universiteService.addUniversite(universite);
    }


    @PutMapping("/updateUniversite/{id}")
    public Universite updateUniversite(@PathVariable("id") long idUniversite, @RequestBody Universite universite) {

        return universiteService.updateUniversite(universite);
    }


    @GetMapping("/getById/{id}")
    public Universite retrieveUniversite(@PathVariable("id") long idUniversite) {
        return universiteService.retrieveUniversite(idUniversite);
    }




    // Affecter un foyer à une université
    @PutMapping("/affecter/{idFoyer}/{nomUniversite}")
    public Foyer affecterFoyerAUniversite(
            @PathVariable("idFoyer") long idFoyer,
            @PathVariable("nomUniversite") String nomUniversite) {

        return universiteService.affecterFoyerAUniversite(idFoyer, nomUniversite);

    }

    // Désaffecter un foyer d'une université (optionnel)
    @PutMapping("/desaffecter-foyer/{idUniversite}")
    public ResponseEntity<Universite> desaffecterFoyerAUniversite(@PathVariable long idUniversite) {
        try {
            // Appeler le service pour désaffecter le foyer
            Universite universite = universiteService.desaffecterFoyerAUniversite(idUniversite);

            // Retourner une réponse HTTP 200 avec l'université mise à jour
            return ResponseEntity.ok(universite);
        } catch (RuntimeException e) {
            // Retourner une réponse HTTP 400 en cas d'erreur (par exemple, université non trouvée)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
