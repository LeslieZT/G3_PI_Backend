package dh.backend.music_store.dto.auth.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDto {
    private Integer id;
    private String name;
    private String description;
    private Double pricePerHour;
    private Integer stockQuantity;
    private Boolean isAvailable;
    private List<String> categoryNames;
    private String brandName;
    private String model;
    private String productCondition;
    private String origin;
    private String launchYear;
    private String productSize;
    private String material;
    private String recommendedUse;
    private List<String> imageUrls;
}

