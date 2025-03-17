package dh.backend.music_store.dto.product.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveProductRequestDto {
    private String name;
    private String imageUrl;
    private Double price;
    private String description;
    private List<Integer> categoryIds;
    private Integer brandId;
    private String model;
    private String productCondition;
    private String origin;
    private String launchYear;
    private String material;
    private String size;
    private String recommendedUse;
}
