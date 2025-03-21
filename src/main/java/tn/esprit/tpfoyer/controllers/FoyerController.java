package tn.esprit.tpfoyer.controllers;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.entities.Foyer;
import tn.esprit.tpfoyer.services.FoyerServiceImpl;
import tn.esprit.tpfoyer.services.IFoyerServices;

@RestController
@RequestMapping("/foyer")
@AllArgsConstructor
public class FoyerController {
    @Autowired
    IFoyerServices foyerService;

    @PostMapping("/saveFoyer")
    public Foyer saveFoyer(@RequestBody Foyer foyer) {
        return foyerService.save(foyer);
    }

    @GetMapping("/getById/{id}")
    public Foyer getFoyer(@PathVariable("id") Long id) {
        return foyerService.findById(id);
    }
    @GetMapping("/getByNomCapacite/{nom}/{capacite}")
    public Foyer getFoyer(@PathVariable("nom") String nom, @PathVariable("capacite") Long capacite) {
        return foyerService.getNomCapacite(nom, capacite);
    }
    @PostMapping("/ajouterFoyerEtAffecterAUniversite/{idUniversite}")
    public ResponseEntity<Foyer> ajouterFoyerEtAffecterAUniversite(
            @RequestBody Foyer foyer, @PathVariable long idUniversite) {
        Foyer createdFoyer = foyerService.ajouterFoyerEtAffecterAUniversite(foyer, idUniversite);
        return new ResponseEntity<>(createdFoyer, HttpStatus.CREATED);
    }
}