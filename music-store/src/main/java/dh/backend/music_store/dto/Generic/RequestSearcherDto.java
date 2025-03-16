package dh.backend.music_store.dto.Generic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestSearcherDto {
    private String text;
    private LocalDate dateInit;
    private LocalDate dateEnd;
}
