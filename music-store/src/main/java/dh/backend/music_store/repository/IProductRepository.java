package dh.backend.music_store.repository;

import dh.backend.music_store.dto.product.projection.FilteredProductProjection;
import dh.backend.music_store.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {

    @Query(value ="SELECT P.id , P.name, PI.url , P.price_per_hour as pricePerHour " +
            "FROM products P " +
            "LEFT JOIN product_images PI ON P.id = PI.product_id AND PI.is_primary = true " +
            "WHERE (:search IS NULL OR LOWER(P.name) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND (:hasCategories = false OR P.category_id IN (:categoryIds)) " +
            "ORDER BY P.id DESC " +
            "LIMIT :limit OFFSET :offset",
            nativeQuery = true)
    List<FilteredProductProjection> filterProducts(@Param("search") String search,
                                                   @Param("categoryIds") List<Integer> categoryIds,
                                                   @Param("hasCategories") boolean hasCategories,
                                                   @Param("limit") int limit,
                                                   @Param("offset") int offset);

    @Query(value = "SELECT COUNT(P.id) FROM products P " +
            "WHERE (:search IS NULL OR LOWER(P.name) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND (:hasCategories = false OR P.category_id IN (:categoryIds))", nativeQuery = true)
    Integer countFilterProducts(@Param("search") String search,
                                @Param("categoryIds") List<Integer> categoryIds,
                                @Param("hasCategories") boolean hasCategories);
}
