package dh.backend.music_store.service.impl;

import dh.backend.music_store.entity.Product;
import dh.backend.music_store.entity.ProductImage;
import dh.backend.music_store.repository.IProductImageRepository;
import dh.backend.music_store.service.IProductImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductImageService implements IProductImageService {
    private final Logger log = LoggerFactory.getLogger(ProductImageService.class);
    private IProductImageRepository productImageRepository;

    public ProductImageService(IProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    @Override
    public Optional<ProductImage> buscarPorId(Integer id) {
        log.info("Ingresando al Service ProductoxImagen | Buscar Imagen por id");
        return productImageRepository.findById(id);
    }

    @Override
    public ProductImage encontrarImagenPrincipalProducto(Product product) {
        log.info("Ingresando al Service ProductoxImagen | Buscar Imagen Principal de producto");
        return productImageRepository.findByProductAndIsPrimary(product);
    }


}
