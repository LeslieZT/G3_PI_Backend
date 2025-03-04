package dh.backend.music_store.service;

import dh.backend.music_store.entity.Product;
import dh.backend.music_store.entity.ProductImage;

import java.util.Optional;

public interface IProductImageService {
    Optional<ProductImage> buscarPorId(Integer id);

    ProductImage encontrarImagenPrincipalProducto(Product product);
}
