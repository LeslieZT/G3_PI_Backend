package dh.backend.music_store.dto.Generic;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto <T> {
    private T data;
}
