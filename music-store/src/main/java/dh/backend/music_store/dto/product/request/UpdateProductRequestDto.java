package dh.backend.music_store.dto.product.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequestDto {
    @NotNull(message = "El id del producto a modificar es obligatorio")
    @Min(value = 1, message = "ID de producto invalido")
    private Integer id;
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 100, message = "El nombre no puede tener mas de 100 caracteres")
    private String name;
    @NotBlank(message = "La Url de la imagen es obligatoria")
    private String imageUrl;
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que cero")
    private Double price;
    @NotBlank(message = "La descripcion es obligatoria")
    private String description;
    @NotNull(message = "La categoria es obligatoria")
    @Min(value = 1, message = "ID de categoria invalido")
    private Integer categoryId;
    @NotNull(message = "La marca es obligatoria")
    @Min(value = 1, message = "ID de marca invalido")
    private Integer brandId;

    private String model;
    private String productCondition;
    private String origin;
    private String launchYear;
    private String material;
    private String size;
    private String recommendedUse;
}
