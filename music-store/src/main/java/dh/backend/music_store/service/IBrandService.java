package dh.backend.music_store.service;

import dh.backend.music_store.dto.Generic.ResponseDto;
import dh.backend.music_store.dto.brand.BrandResponseDto;

import java.util.List;

public interface IBrandService {
    ResponseDto<List<BrandResponseDto>> findAll();

    BrandResponseDto findById(Integer id);
}
