package dh.backend.music_store.dto.product.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSearchProductDto {

        private Integer id;
        private String name;
        private String description;
        private Double pricePerHour;
        private Integer stockQuantity;
        private Boolean isAvailable;
        private String category;
        private String images;
        private LocalDate creationDate;
        private String brand;
        private String model;
        private String product_condition;
        private String origin;
        private String launchYear;
        private String product_ize;
        private String material;
        private String recommendedUse;

}
