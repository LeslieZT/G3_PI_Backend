package dh.backend.music_store.dto.reservation.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationByProductResponseDto {
    private Integer id;
    private LocalDate startDate;
    private LocalDate endDate;
}
