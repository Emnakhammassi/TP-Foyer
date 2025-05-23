package tn.esprit.tpfoyer.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.entities.Bloc;
import tn.esprit.tpfoyer.entities.Chambre;
import tn.esprit.tpfoyer.services.ChambreServiceImpl;
import tn.esprit.tpfoyer.services.IBlocServices;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/bloc")
@RequiredArgsConstructor
public class BlocController {

    private final IBlocServices blocService;
    private final ChambreServiceImpl chambreService;

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
//    @PutMapping("/affecterChambres/{idBloc}")
//    public ResponseEntity<Bloc> affecterChambres(@RequestBody List<Long> numChambres, @PathVariable long idBloc) {
//        Bloc bloc = blocService.affecterChambresABloc(numChambres, idBloc);
//        return ResponseEntity.ok(bloc);
//    }

    @PutMapping("/{idBloc}/affecter-chambres")
    public ResponseEntity<?> affecterChambresABloc(
            @RequestBody List<Long> numChambres,
            @PathVariable Long idBloc) {

        try {
            Bloc bloc = blocService.affecterChambresABloc(numChambres, idBloc);
            return ResponseEntity.ok(bloc);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


//    @PostMapping("/{blocId}/chambres/from-bloc")
//    public ResponseEntity<?> addChambreFromBloc(
//            @RequestBody Bloc blocRequest,
//            @PathVariable Long blocId) {
//
//        try {
//            // Vérification basique
//            if (blocRequest.getChambres() == null || blocRequest.getChambres().isEmpty()) {
//                return ResponseEntity.badRequest().body("Aucune chambre fournie dans le bloc");
//            }
//
//            // Ajout de chaque chambre fournie dans le blocRequest
//            List<Chambre> savedChambres = new ArrayList<>();
//            for (Chambre c : blocRequest.getChambres()) {
//                Chambre saved = chambreService.addChambreToBloc(c, blocId);
//                savedChambres.add(saved);
//            }
//
//            return ResponseEntity.status(HttpStatus.CREATED).body(savedChambres);
//
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }



}
