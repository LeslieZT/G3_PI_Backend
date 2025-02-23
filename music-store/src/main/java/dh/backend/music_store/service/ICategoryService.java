package dh.backend.music_store.service;

import dh.backend.music_store.dto.Generic.ResponseDto;
import dh.backend.music_store.dto.category.CategoryResponseDto;

import java.util.List;

public interface ICategoryService {
    ResponseDto<List<CategoryResponseDto>> findAll();
}
