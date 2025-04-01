package dh.backend.music_store.dto.Generic;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestSearcherDto {

    private String text;
    @DateTimeFormat
    private LocalDate dateInit;
    @DateTimeFormat
    private LocalDate dateEnd;

}
