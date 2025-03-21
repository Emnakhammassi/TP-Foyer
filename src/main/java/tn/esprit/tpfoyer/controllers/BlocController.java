package tn.esprit.tpfoyer.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.entities.Bloc;
import tn.esprit.tpfoyer.services.IBlocServices;

import java.util.List;

@RestController
@RequestMapping("/bloc")
@AllArgsConstructor
public class BlocController {
    @Autowired
    IBlocServices blocService;


    @GetMapping("/getAllBlocs")
    public List<Bloc> retrieveBlocs() {
        return blocService.retrieveBlocs();
    }


    @GetMapping("/getById/{id}")
    public Bloc retrieveBloc(@PathVariable("id") long idBloc) {
        return blocService.retrieveBloc(idBloc);
    }

    @PostMapping("/addBloc")
    public Bloc addBloc(@RequestBody Bloc bloc) {
        return blocService.addBloc(bloc);
    }

    // Mettre à jour un bloc existant
    @PutMapping("/updateBloc/{id}")
    public Bloc updateBloc(@PathVariable("id") long idBloc, @RequestBody Bloc bloc) {

        return blocService.updateBloc(bloc);
    }

    // Supprimer un bloc par ID
    @DeleteMapping("/removeBloc/{id}")
    public void removeBloc(@PathVariable("id") long idBloc) {
        blocService.removeBloc(idBloc);
    }
    // Affecter une liste de chambres à un bloc
//    @PutMapping("/affecterChambresABloc/{idBloc}")
//    public Bloc affecterChambresABloc(@PathVariable long idBloc, @RequestBody List<Long> numChambre) {
//        return blocService.affecterChambresABloc(numChambre, idBloc);
//    }
    @PutMapping("affecterBlocAFoyer/{nomFoyer}/{nomBloc}")
    Bloc affecterBlocAFoyer(@PathVariable String nomBloc, @PathVariable String nomFoyer) {
        return blocService.affecterBlocAFoyer(nomBloc, nomFoyer);
    }

    @PutMapping("/affecterChambresABloc/{idBloc}")
    public Bloc affecterChambresABloc(
            @PathVariable long idBloc, // idBloc dans le chemin
            @RequestBody List<Long> numChambres // Liste de numChambres dans le corps
    ) {
        return blocService.affecterChambresABloc(numChambres, idBloc);
    }
}