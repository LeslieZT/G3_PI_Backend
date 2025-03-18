package dh.backend.music_store.repository;

import dh.backend.music_store.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByName(String name);
}
