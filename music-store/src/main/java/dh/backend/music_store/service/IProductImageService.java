package dh.backend.music_store.service;

import dh.backend.music_store.entity.Product;
import dh.backend.music_store.entity.ProductImage;

import java.util.List;
import java.util.Optional;

public interface IProductImageService {
    Optional<ProductImage> findById(Integer id);

    ProductImage findByProductAndIsPrimary(Product product);

    List<ProductImage> findByProductAndIsNotPrimary(Product product);
}
