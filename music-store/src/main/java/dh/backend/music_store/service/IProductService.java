package dh.backend.music_store.service;

import dh.backend.music_store.dto.Generic.PaginationResponseDto;
import dh.backend.music_store.dto.Generic.ResponseDto;
import dh.backend.music_store.dto.auth.response.ProductResponseDto;
import dh.backend.music_store.dto.product.request.FindAllProductRequestDto;
import dh.backend.music_store.dto.product.request.SaveProductRequestDto;
import dh.backend.music_store.dto.product.response.DetailProductResponseDto;
import dh.backend.music_store.dto.product.response.FindAllProductResponseDto;
import dh.backend.music_store.dto.product.response.FindOneProductResponseDto;
import dh.backend.music_store.entity.Product;

import java.util.List;

public interface IProductService {
    PaginationResponseDto<FindAllProductResponseDto> findAll(FindAllProductRequestDto request);
    FindOneProductResponseDto findOne(Integer id);
    DetailProductResponseDto findDetailsById(Integer id);
    DetailProductResponseDto save(SaveProductRequestDto saveProductRequestDto);
    //añado
    DetailProductResponseDto assignCategoryToProduct(Integer productId, Integer categoryId);

    ResponseDto<List<ProductResponseDto>> findAll();

    ProductResponseDto findById(Integer id);

    Product getProductByName(String name);

    List<ProductResponseDto> findByCategory(String categoryName); // Nuevo método para buscar por categoría

    List<ProductResponseDto> findByCategoryId(Integer categoryId); // Nuevo método para buscar por ID de categoría

    List<ProductResponseDto> filterProducts(String search, List<Integer> categoryIds, boolean hasCategories, int limit, int offset);
}
