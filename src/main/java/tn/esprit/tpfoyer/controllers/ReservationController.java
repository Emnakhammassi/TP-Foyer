package tn.esprit.tpfoyer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.entities.Reservation;
import tn.esprit.tpfoyer.services.IReservationServices;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final IReservationServices reservationService;

    // Récupérer toutes les réservations
    @GetMapping("/all")
    public List<Reservation> getAllReservations() {
        return reservationService.retrieveAllReservation();
    }

    // Mettre à jour une réservation
    @PutMapping("/update")
    public Reservation updateReservation(@RequestBody Reservation res) {
        return reservationService.updateReservation(res);
    }

    // Récupérer une réservation par ID
    @GetMapping("/get/{id}")
    public Reservation getReservation(@PathVariable Long id) {
        return reservationService.retrieveReservation(id);
    }

    // Ajouter une réservation en fonction de l'ID du bloc et du CIN de l'étudiant
    @PostMapping("/ajouter/{idBloc}/{cinEtudiant}")
    public Reservation ajouterReservation(@PathVariable Long idBloc, @PathVariable Long cinEtudiant) {
        return reservationService.ajouterReservation(idBloc, cinEtudiant);
    }

    // Annuler une réservation en fonction du CIN de l'étudiant
    @PutMapping("/annuler/{cinEtudiant}")
    public ResponseEntity<Reservation> annulerReservation(@PathVariable Long cinEtudiant) {
        Reservation annulee = reservationService.annulerReservation(cinEtudiant);
        return ResponseEntity.ok(annulee);
    }

    // Récupérer les réservations par année universitaire et par université
    @GetMapping("/by-year-and-university")
    public ResponseEntity<List<Reservation>> getReservationsByYearAndUniversity(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date anneeUniversite,
            @RequestParam String nomUniversite) {

        List<Reservation> reservations = reservationService
                .getReservationParAnneeUniversitaireEtNomUniversite(anneeUniversite, nomUniversite);

        return ResponseEntity.ok(reservations);
    }
}
