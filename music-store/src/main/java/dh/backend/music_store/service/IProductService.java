package dh.backend.music_store.service;

import dh.backend.music_store.dto.Generic.PaginationResponseDto;
import dh.backend.music_store.dto.product.request.FindAllProductRequestDto;
import dh.backend.music_store.dto.product.response.FindAllProductResponseDto;
import dh.backend.music_store.dto.product.response.FindOneProductResponseDto;

public interface IProductService {
    PaginationResponseDto<FindAllProductResponseDto> findAll(FindAllProductRequestDto request);
    FindOneProductResponseDto findOne(Integer id);
}
