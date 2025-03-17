package dh.backend.music_store.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import dh.backend.music_store.utils.GsonProvider;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "products")
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
    //*
    @ManyToMany
    @JoinTable(
            name = "product_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductImage> images;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brandId;
    private String model;
    private String product_condition;
    private String origin;
    @Column(name = "launch_year")
    private String launchYear;
    private String product_size;
    private String material;
    @Column (name = "recommended_use")
    private String recommendedUse;

    @Override
    public String toString() {
        return GsonProvider.getGson().toJson(this);
    }



}
