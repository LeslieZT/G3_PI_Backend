package dh.backend.music_store.repository;

import dh.backend.music_store.entity.Product;
import dh.backend.music_store.entity.ProductImage;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductImageRepository extends JpaRepository<ProductImage, Integer> {

    //devuelve la imagen portada de un producto.
    @Query("Select p from ProductImage p where p.product = :productoABuscar AND p.isPrimary = true")
    ProductImage findByProductAndIsPrimary(Product productoABuscar);
}
