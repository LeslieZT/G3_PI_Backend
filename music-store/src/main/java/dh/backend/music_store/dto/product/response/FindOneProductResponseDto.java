
package dh.backend.music_store.dto.product.response;
import dh.backend.music_store.dto.brand.BrandResponseDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FindOneProductResponseDto {
    private Integer id;
    private String name;
    private String description;
    private Double pricePerHour;
    private Integer stockQuantity;
    private Boolean isAvailable;
    private ProductCategoryResponseDto category;
    //private BrandResponseDto brand;
    private List<ProductImageResponseDto> images;
}
