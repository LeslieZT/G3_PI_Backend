package dh.backend.music_store.dto.product.request;

<<<<<<< HEAD
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
=======
import jakarta.validation.constraints.*;
>>>>>>> 3e7ae0d6b419aa199c3ffbe5cc4195959741bd0b
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

<<<<<<< HEAD
import java.util.List;

=======
>>>>>>> 3e7ae0d6b419aa199c3ffbe5cc4195959741bd0b
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequestDto {
<<<<<<< HEAD
    private String name;
    private List<String> imageUrls;     //puede ser private String imageUrl; si solo manejamos una imagen
    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser positivo")
    private Double price;
    private String description;
    private Integer brandId;
=======
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

>>>>>>> 3e7ae0d6b419aa199c3ffbe5cc4195959741bd0b
    private String model;
    private String productCondition;
    private String origin;
    private String launchYear;
    private String material;
    private String size;
    private String recommendedUse;
<<<<<<< HEAD
}
=======
}
>>>>>>> 3e7ae0d6b419aa199c3ffbe5cc4195959741bd0b
