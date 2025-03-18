package dh.backend.music_store.repository;

import dh.backend.music_store.dto.product.projection.FilteredProductProjection;
import dh.backend.music_store.entity.Category;
import dh.backend.music_store.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {

    // Nueva consulta para filtrar productos considerando múltiples categorías
    @Query(value ="SELECT P.id, P.name, PI.url, P.price_per_hour AS pricePerHour, C.name AS categoryName, P.description " +
            "FROM products P " +
            "LEFT JOIN product_images PI ON P.id = PI.product_id AND PI.is_primary = true " +
            "INNER JOIN product_categories PC ON P.id = PC.product_id " + // Nuevo join con la tabla intermedia
            "INNER JOIN categories C ON PC.category_id = C.id " +
            "WHERE (:search IS NULL OR LOWER(P.name) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND (:hasCategories = false OR PC.category_id IN (:categoryIds)) " +
            "ORDER BY P.id DESC " +
            "LIMIT :limit OFFSET :offset",
            nativeQuery = true)
    List<FilteredProductProjection> filterProducts(@Param("search") String search,
                                                   @Param("categoryIds") List<Integer> categoryIds,
                                                   @Param("hasCategories") boolean hasCategories,
                                                   @Param("limit") int limit,
                                                   @Param("offset") int offset);

    // Contar productos filtrados considerando múltiples categorías
    @Query(value = "SELECT COUNT(DISTINCT P.id) FROM products P " +
            "INNER JOIN product_categories PC ON P.id = PC.product_id " +
            "WHERE (:search IS NULL OR LOWER(P.name) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND (:hasCategories = false OR PC.category_id IN (:categoryIds))", nativeQuery = true)
    Integer countFilterProducts(@Param("search") String search,
                                @Param("categoryIds") List<Integer> categoryIds,
                                @Param("hasCategories") boolean hasCategories);

    // Métodos para buscar productos por categoría (JPA derivado)
    List<Product> findByCategory_Name(String name);
    List<Product> findByCategory_Id(Integer id);
    List<Product> findByCategoryIn(List<Category> categories);

    // Buscar producto por nombre exacto
    Optional<Product> findByName(String name);
}
