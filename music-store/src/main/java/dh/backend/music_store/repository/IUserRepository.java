package dh.backend.music_store.repository;
import dh.backend.music_store.dto.user.projection.FilteredUserProjection;
import dh.backend.music_store.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<Users, Integer> {

    @Query(value =
            "SELECT U.id AS id, U.first_name AS firstName, U.last_name AS lastName, " +
            "U.email AS email, U.role_id AS roleId, R.name AS roleName " +
            "FROM users U " +
            "INNER JOIN roles R ON U.role_id = R.id " +
            "WHERE (:search IS NULL OR " +
            "LOWER(U.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(U.first_name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(U.last_name) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND (:hasRoles = false OR U.role_id IN (:roleIds)) " +
            "ORDER BY U.id DESC " +
            "LIMIT :limit OFFSET :offset",
            nativeQuery = true)
    List<FilteredUserProjection> filterUsers(@Param("search") String search,
                                             @Param("roleIds") List<Integer> roleIds,
                                             @Param("hasRoles") boolean hasRoles,
                                             @Param("limit") int limit,
                                             @Param("offset") int offset);



    @Query(value = "SELECT COUNT(U.id) FROM users U " +
            "INNER JOIN roles R ON U.role_id = R.id " +
            "WHERE (:search IS NULL OR " +
            "LOWER(U.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(U.first_name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(U.last_name) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND (:hasRoles = false OR U.role_id IN (:roleIds)) " ,
            nativeQuery = true)
    Integer countFilterUsers(@Param("search") String search,
                             @Param("roleIds") List<Integer> roleIds,
                             @Param("hasRoles") boolean hasRoles);
    //añadido
    boolean existsByEmail(String email);

    Optional<Users> findByEmail(String email);
}
