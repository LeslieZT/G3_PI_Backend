package dh.backend.music_store.dto.product;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FindAllProductResponseDto {
    private Integer id;
    private String name;
    private String imageUrl;
    private Double pricePerHour;
}

