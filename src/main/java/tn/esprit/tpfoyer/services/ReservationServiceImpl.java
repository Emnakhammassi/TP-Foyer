package tn.esprit.tpfoyer.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entities.*;
import tn.esprit.tpfoyer.repositories.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationServices {

    private final IReservationRepository reservationRepository;
    private final IChambreRepository chambreRepository;
    private final IEtudiantRepository etudiantRepository;
    private final IUniversiteRepository universiteRepository;
    private final IBlocRepository blocRepository;

    @Override
    public List<Reservation> retrieveAllReservation() {
        return reservationRepository.findAll();  // Récupère toutes les réservations
    }

    @Override
    public Reservation updateReservation(Reservation res) {
        return reservationRepository.save(res);  // Met à jour la réservation
    }

    @Override
    public Reservation retrieveReservation(Long idReservation) {
        // Récupère une réservation par son ID
        return reservationRepository.findByIdReservation(idReservation);
    }

    @Override
    public Reservation ajouterReservation(Long idBloc, Long cinEtudiant) {
        // 1. Récupère le bloc et l'étudiant
        Bloc bloc = blocRepository.findById(idBloc).orElseThrow(() -> new RuntimeException("Bloc non trouvé"));
        Etudiant etudiant = etudiantRepository.findByCin(cinEtudiant);
        if (etudiant == null) {
            throw new EntityNotFoundException("Étudiant non trouvé avec le CIN: " + cinEtudiant);
        }

        // 2. Trouve une chambre disponible dans le bloc donné
        Chambre chambre = findAvailableChambre(bloc);

        // 3. Vérifie si la chambre a encore de la capacité
        if (chambre == null || !hasCapacity(chambre)) {
            throw new RuntimeException("Pas de capacité disponible dans cette chambre");
        }

        // 4. Crée une nouvelle réservation
        Reservation reservation = new Reservation();
        reservation.setAnneeUniversitaire(new Date()); // Utilise l'année actuelle comme exemple
        reservation.setEstValide(true);

        // 5. Formate le numéro de réservation
        String numReservation = chambre.getNumeroChambre() + "-" + bloc.getNomBloc() + "-" + reservation.getAnneeUniversitaire().getYear();
        reservation.setNumReservation(numReservation);

        // 6. Ajoute l'étudiant à la réservation
        reservation.setEtudiants(Set.of(etudiant));

        // 7. Sauvegarde la réservation
        return reservationRepository.save(reservation);
    }

    // Méthode pour vérifier si la chambre a de la capacité
    private boolean hasCapacity(Chambre chambre) {
        long totalCapacity = 0;
        switch (chambre.getTypeC()) {
            case SIMPLE: totalCapacity = 1; break;
            case DOUBLE: totalCapacity = 2; break;
            case TRIPLE: totalCapacity = 3; break;
        }
        return chambre.getReservations().size() < totalCapacity;  // Vérifie si le nombre de réservations est inférieur à la capacité
    }

    // Méthode pour trouver une chambre disponible dans un bloc donné
    private Chambre findAvailableChambre(Bloc bloc) {
        for (Chambre chambre : bloc.getChambres()) {
            if (hasCapacity(chambre)) {
                return chambre;  // Retourne la première chambre disponible
            }
        }
        return null;  // Aucune chambre disponible
    }

    @Override
    public Reservation annulerReservation(Long cinEtudiant) {
        // 1. Trouve l'étudiant
        Etudiant etudiant = etudiantRepository.findByCin(cinEtudiant);
        if (etudiant == null) {
            throw new EntityNotFoundException("Étudiant non trouvé avec le CIN: " + cinEtudiant);
        }

        // 2. Trouve la réservation active de l'étudiant
        Reservation reservation = reservationRepository.findByEtudiantsContainsAndEstValideTrue(etudiant)
                .orElseThrow(() -> new EntityNotFoundException("Aucune réservation active trouvée pour cet étudiant"));

        // 3. Désactive la réservation
        reservation.setEstValide(false);

        // 4. Retire l'étudiant de la réservation
        reservation.getEtudiants().remove(etudiant);

        // 5. Sauvegarde les modifications de la réservation
        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getReservationParAnneeUniversitaireEtNomUniversite(
            Date anneeUniversite, String nomUniversite) {

        // Vérifie si l'université existe avant de continuer
        Universite universite = universiteRepository.findByNomUniversite(nomUniversite);
        if (universite == null) {
            throw new EntityNotFoundException("Université non trouvée : " + nomUniversite);
        }

        // Récupère les réservations pour une année universitaire et une université spécifiques
        return reservationRepository.findByAnneeAndUniversite(anneeUniversite, nomUniversite);
    }
}
