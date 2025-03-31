package dh.backend.music_store.dto.reservation.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveReservationRequestDto {

    @NotBlank(message = "La fecha de inicio no puede estar vacía")
    @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2}$",
            message = "La fecha de inicio debe tener el formato YYYY-MM-DD"
    )
    private String startDate;

    @NotBlank(message = "La fecha de devolucion no puede estar vacía")
    @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2}$",
            message = "La fecha de devolucion debe tener el formato YYYY-MM-DD"
    )
    private String endDate;

    @NotBlank(message = "El correo del usuario no puede estar vacío")
    @Email(message = "El correo del usuario debe ser válido")
    private String userEmail;

    @NotNull(message = "El ID del producto no puede ser nulo")
    @Min(value = 1, message = "ID de producto invalido")
    private Integer productId;
}
