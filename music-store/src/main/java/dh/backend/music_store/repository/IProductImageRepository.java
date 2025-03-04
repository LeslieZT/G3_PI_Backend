package dh.backend.music_store.repository;

import dh.backend.music_store.entity.Product;
import dh.backend.music_store.entity.ProductImage;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductImageRepository extends JpaRepository<ProductImage, Integer> {

    //devuelve la imagen principal de un producto.
    @Query("Select p from ProductImage p where p.product = :productToSearch AND p.isPrimary = true")
    ProductImage findByProductAndIsPrimary(Product productToSearch);

    @Query("Select p from ProductImage p where p.product = :productToSearch AND p.isPrimary = false")
    List<ProductImage> findByProductAndIsNotPrimary(Product productToSearch);


}
