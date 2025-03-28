package dh.backend.music_store.service;

import dh.backend.music_store.dto.Generic.PaginationResponseDto;
import dh.backend.music_store.dto.Generic.RequestSearcherDto;
import dh.backend.music_store.dto.product.request.FindAllProductRequestDto;
import dh.backend.music_store.dto.product.request.SaveProductRequestDto;
import dh.backend.music_store.dto.product.request.UpdateProductRequestDto;
import dh.backend.music_store.dto.product.response.DetailProductResponseDto;
import dh.backend.music_store.dto.product.response.FindAllProductResponseDto;
import dh.backend.music_store.dto.product.response.FindOneProductResponseDto;
import dh.backend.music_store.dto.product.response.ResponseSearchProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    PaginationResponseDto<FindAllProductResponseDto> findAll(FindAllProductRequestDto request);
    FindOneProductResponseDto findOne(Integer id);
    DetailProductResponseDto findDetailsById(Integer id);
    DetailProductResponseDto save(SaveProductRequestDto saveProductRequestDto);
    void update(UpdateProductRequestDto updateProductRequestDto);

    PaginationResponseDto<ResponseSearchProductDto> searchProducts(RequestSearcherDto requestSearcherDto, Pageable pageable);
}
