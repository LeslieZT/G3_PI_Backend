package dh.backend.music_store.dto.category;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDto {
    private Integer id;
    private String name;
    private String imageUrl;
}
