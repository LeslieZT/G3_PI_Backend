package dh.backend.music_store.repository;


import dh.backend.music_store.dto.reservation.projection.ReservationByProductProjection;
import dh.backend.music_store.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query(value = "SELECT id, start_date, end_date FROM reservations " +
            "WHERE product_id = :productId " +
            "AND status IN ('PENDING', 'APPROVED', 'IN_PROGRESS') " +
            "AND (start_date >= CURDATE() OR end_date >= CURDATE()) " +
            "ORDER BY start_date",
            nativeQuery = true)
    List<ReservationByProductProjection> findReservationsByProduct(@Param("productId") Integer productId);

    List<Reservation> findByProductId(Integer id);

}
