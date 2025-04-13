package tn.esprit.tpfoyer.services;

import java.util.Date;
import java.util.List;
import tn.esprit.tpfoyer.entities.Reservation;

public interface IReservationServices {

    // Récupère toutes les réservations
    List<Reservation> retrieveAllReservation();

    // Met à jour une réservation existante
    Reservation updateReservation(Reservation res);

    // Récupère une réservation spécifique par son ID
    Reservation retrieveReservation(Long idReservation);

    // Ajoute une réservation pour un étudiant donné dans un bloc donné
    Reservation ajouterReservation(Long idBloc, Long cinEtudiant);

    // Annule la réservation d'un étudiant en fonction de son CIN
    Reservation annulerReservation(Long cinEtudiant);

    // Récupère les réservations par année universitaire et nom de l'université
    List<Reservation> getReservationParAnneeUniversitaireEtNomUniversite(Date anneeUniversite, String nomUniversite);
}
