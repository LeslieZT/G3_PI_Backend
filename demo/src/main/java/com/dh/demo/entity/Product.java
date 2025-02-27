package com.dh.demo.entity;


import com.dh.demo.utils.JacksonProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    @Column(name = "price_per_hour")
    private Double pricePerHour;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Column(name = "is_available")
    private Boolean isAvailable = true;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brandId;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductImage> images;

    @Column(name = "creation_date")
    private LocalDate creationDate;


    @Override
    public String toString() {
        try {
            return JacksonProvider.getObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        //GsonProvider.getGson().toJson(this);
    }


}
