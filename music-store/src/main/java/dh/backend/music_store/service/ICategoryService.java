package dh.backend.music_store.service;

import dh.backend.music_store.dto.Generic.ResponseDto;
import dh.backend.music_store.dto.category.CategoryResponseDto;
import dh.backend.music_store.entity.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    ResponseDto<List<CategoryResponseDto>> findAll();

    CategoryResponseDto findById(Integer id);
}
