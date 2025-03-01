package dh.backend.music_store.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import dh.backend.music_store.utils.GsonProvider;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductImage> images;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    private String marca;
    private String modelo;
    private String condicion;
    private String origen;
    @Column(name = "anio_lanzamiento")
    private String anioLanzamiento;
    private String medidas;
    private String material;
    @Column (name = "uso_recomendado")
    private String usoRecomendado;
    @Override
    public String toString() {
        return GsonProvider.getGson().toJson(this);
    }



}
