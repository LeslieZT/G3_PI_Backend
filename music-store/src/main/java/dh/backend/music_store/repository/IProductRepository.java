package dh.backend.music_store.repository;

import dh.backend.music_store.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT P.id, P.name, PI.url, P.pricePerHour " +
            "FROM Product P " +
            "LEFT JOIN P.images PI ON PI.isPrimary = true " +
            "WHERE (:search IS NULL OR LOWER(P.name) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND (:categoryId IS NULL OR P.category.id = :categoryId) " +
            "ORDER BY P.id DESC " +
            "LIMIT :limit OFFSET :offset")
    List<Object[]> filterProducts(@Param("search") String search,
                                @Param("categoryId") Integer categoryId,
                                @Param("limit") int limit,
                                @Param("offset") int offset);


    @Query("SELECT COUNT(P.id) FROM Product P " +  // Espacio agregado después de 'P'
            "WHERE (:search IS NULL OR LOWER(P.name) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND (:categoryId IS NULL OR P.category.id = :categoryId)")
    Integer countFilterProducts(@Param("search") String search,
                             @Param("categoryId") Integer categoryId);

}
