package dh.backend.music_store.dto.Generic;

import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponseDto<T>{
    private List<T> data;
    private int page;
    private int size;
    private int total;
}
