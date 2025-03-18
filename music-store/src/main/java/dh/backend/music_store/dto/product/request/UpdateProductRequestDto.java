package dh.backend.music_store.dto.product.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequestDto {
    private String name;
    private List<String> imageUrls;     //puede ser private String imageUrl; si solo manejamos una imagen
    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser positivo")
    private Double price;
    private String description;
    private Integer brandId;
    private String model;
    private String productCondition;
    private String origin;
    private String launchYear;
    private String material;
    private String size;
    private String recommendedUse;
}