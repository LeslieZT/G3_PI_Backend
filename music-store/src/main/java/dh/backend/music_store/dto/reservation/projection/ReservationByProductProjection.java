package dh.backend.music_store.dto.reservation.projection;

import java.time.LocalDate;

public interface ReservationByProductProjection {
    Integer getId();
    LocalDate getStartDate();
    LocalDate getEndDate();
}
