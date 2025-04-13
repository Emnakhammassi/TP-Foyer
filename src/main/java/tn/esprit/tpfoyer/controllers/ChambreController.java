package tn.esprit.tpfoyer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import tn.esprit.tpfoyer.entities.Chambre;
import tn.esprit.tpfoyer.entities.TypeChambre;
import tn.esprit.tpfoyer.services.IChambreServices;

import java.util.List;

@RestController
@RequestMapping("/chambres")
@RequiredArgsConstructor
public class ChambreController {

    private final IChambreServices chambreService;

    // Ajouter une chambre
    @PostMapping
    public Chambre addChambre(@RequestBody Chambre c) {
        return chambreService.addChambre(c);
    }

    // Mettre à jour une chambre existante
    @PutMapping("/{id}")
    public Chambre updateChambre(@PathVariable Long id, @RequestBody Chambre c) {
        return chambreService.updateChambre(id, c);
    }

    // Récupérer une chambre par ID
    @GetMapping("/{id}")
    public Chambre getChambre(@PathVariable Long id) {
        return chambreService.retrieveChambre(id);
    }

    // Récupérer toutes les chambres
    @GetMapping
    public List<Chambre> getAllChambres() {
        return chambreService.retrieveAllChambres();
    }

    // Récupérer les chambres par bloc
    @GetMapping("/bloc/{idBloc}")
    public List<Chambre> getByBloc(@PathVariable Long idBloc) {
        return chambreService.findByBlocId(idBloc);
    }

    // Récupérer les chambres par type (SIMPLE, DOUBLE, TRIPLE...)
    @GetMapping("/type/{type}")
    public List<Chambre> getByType(@PathVariable TypeChambre type) {
        return chambreService.findByType(type);
    }

    // Récupérer les chambres par université
    @GetMapping("/universite/{nomUniversite}")
    public List<Chambre> getByUniversite(@PathVariable String nomUniversite) {
        return chambreService.findByUniversite(nomUniversite);
    }

    // Récupérer les chambres selon le nom de l’université (version avec ResponseEntity)
    @GetMapping("/by-universite/{nomUniversite}")
    public ResponseEntity<List<Chambre>> getChambresByUniversite(@PathVariable String nomUniversite) {
        List<Chambre> chambres = chambreService.getChambresParNomUniversite(nomUniversite);
        return ResponseEntity.ok(chambres);
    }

    // Récupérer les chambres selon bloc et type
    @GetMapping("/bloc/{idBloc}/type/{typeC}")
    public ResponseEntity<List<Chambre>> getChambresByBlocAndType(
            @PathVariable long idBloc,
            @PathVariable TypeChambre typeC) {
        List<Chambre> chambres = chambreService.getChambresParBlocEtType(idBloc, typeC);
        return ResponseEntity.ok(chambres);
    }

    // Récupérer les chambres non réservées d'une université pour un type de chambre donné
    @GetMapping("/non-reservees")
    public ResponseEntity<List<Chambre>> getChambresNonReservees(
            @RequestParam String nomUniversite,
            @RequestParam TypeChambre type) {
        List<Chambre> chambres = chambreService
                .getChambresNonReserveParNomUniversiteEtTypeChambre(nomUniversite, type);
        return ResponseEntity.ok(chambres);
    }
}
