package tn.esprit.tpfoyer.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entities.*;
import tn.esprit.tpfoyer.repositories.IBlocRepository;
import tn.esprit.tpfoyer.repositories.IChambreReposirtory;
import tn.esprit.tpfoyer.repositories.IEtudiantRepository;
import tn.esprit.tpfoyer.repositories.IReservationRepository;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements IReservationServices{
    @Autowired
    IReservationRepository reservationRepository;
    @Autowired
    IChambreReposirtory chambreRepository;
    @Autowired
    IEtudiantRepository etudiantRepository;
    @Autowired
    IBlocRepository blocRepository;


    @Override
    public List<Reservation> retrieveAllReservation() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation updateReservation(Reservation res) {
        if (reservationRepository.existsById(Long.valueOf(res.getIdReservation()))) {
            return reservationRepository.save(res);
        }
        return null;
    }

    @Override
    public Reservation retrieveReservation(Long idReservation) {
        return reservationRepository.findById(idReservation).orElse(null);
    }

    @Override
    public List<Reservation> getReservationParAnneeUniversitaireEtNomUniversite(int anneeUniversitaire, String nomUniversite) {
        return reservationRepository.findReservationsByAnneeAndUniversite(anneeUniversitaire, nomUniversite);    }

    @Override
    public Reservation ajouterReservation(long idBloc, long cinEtudiant) {
        // Fetch Bloc and Etudiant
        Bloc bloc = blocRepository.findById(idBloc).orElseThrow(() -> new RuntimeException("Bloc non trouvé"));
        Etudiant etudiant = etudiantRepository.findByCin(cinEtudiant);

        // Fetch available Chambre for the Bloc
        Chambre chambreDisponible = findAvailableChambre(bloc);

        // If no chambre is available, throw exception
        if (chambreDisponible == null) {
            throw new RuntimeException("Aucune chambre disponible !");
        }

        // Create a new Reservation object
        Reservation reservation = new Reservation();
        reservation.setNumReservation(chambreDisponible.getIdChambre() + "-" + bloc.getNomBloc() + "-2024");
        reservation.setEstValide(true);  // Set the reservation as valid

        // Set the Etudiant and Chambre
        reservation.setChambre(chambreDisponible);
        reservation.setEtudiant(etudiant);

        // Save the reservation
        return reservationRepository.save(reservation);
    }

    private Chambre findAvailableChambre(Bloc bloc) {
        for (Chambre chambre : bloc.getChambres()) {
            // Check if the chambre's type capacity is not exceeded
            int currentCapacity = chambre.getReservations().size();
            int maxCapacity = getMaxCapacityForType(chambre.getTypeC());

            if (currentCapacity < maxCapacity) {
                return chambre; // Room is available
            }
        }
        return null; // No available room
    }

    private int getMaxCapacityForType(TypeChambre typeChambre) {
        switch (typeChambre) {
            case SIMPLE:
                return 1;
            case DOUBLE:
                return 2;
            case TRIPLE:
                return 3;
            default:
                return 0;
        }
    }
}
