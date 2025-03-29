package dh.backend.music_store.dto.reservation.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveReservationRequestDto {
    //no olvidar validaciones
    private String startDate;
    private String endDate;
    private String userEmail;
    private Integer productId;
}
