package dh.backend.music_store.dto.product.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailProductResponseDto {
    private Integer id;
    private String category;
    private String name;
    private String mainImage;
    private String description;
    private Double price;
    private String brand;
    private String model;
    private String product_condition;
    private String origin;
    private String launchYear;
    private String size;
    private String material;
    private String recommendedUse;
    private List<String> secondaryImages;
}
