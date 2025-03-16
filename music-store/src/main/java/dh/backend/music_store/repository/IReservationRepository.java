package dh.backend.music_store.repository;

import dh.backend.music_store.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByProductIdAndStatus(int productId, int status);
}
