package tn.esprit.tpfoyer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.entities.Bloc;
import tn.esprit.tpfoyer.services.IBlocServices;

import java.util.List;

@RestController
@RequestMapping("/bloc")
@RequiredArgsConstructor
public class BlocController {

    private final IBlocServices blocService;

    // Retourne la liste de tous les blocs
    @GetMapping
    public List<Bloc> getAllBlocs() {
        return blocService.retrieveAllBlocs();
    }

    // Ajoute un nouveau bloc
    @PostMapping
    public Bloc addBloc(@RequestBody Bloc bloc) {
        return blocService.addBloc(bloc);
    }

    // Met à jour un bloc existant
    @PutMapping("/{id}")
    public Bloc updateBloc(@PathVariable Long id, @RequestBody Bloc bloc) {
        return blocService.updateBloc(id, bloc);
    }

    // Récupère un bloc par son ID
    @GetMapping("/{id}")
    public Bloc getBloc(@PathVariable Long id) {
        return blocService.retrieveBloc(id);
    }

    // Supprime un bloc par ID
    @DeleteMapping("/{id}")
    public void deleteBloc(@PathVariable Long id) {
        blocService.removeBloc(id);
    }

    // Affecte une liste de chambres à un bloc donné
    @PutMapping("/affecterChambres/{idBloc}")
    public ResponseEntity<Bloc> affecterChambres(@RequestBody List<Long> numChambres, @PathVariable long idBloc) {
        Bloc bloc = blocService.affecterChambresABloc(numChambres, idBloc);
        return ResponseEntity.ok(bloc);
    }
}
