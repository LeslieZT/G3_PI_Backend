package dh.backend.music_store.service.impl;

import dh.backend.music_store.dto.Generic.ResponseDto;
import dh.backend.music_store.dto.category.CategoryResponseDto;
import dh.backend.music_store.entity.Category;
import dh.backend.music_store.exception.ResourceNotFoundException;
import dh.backend.music_store.repository.ICategoryRepository;
import dh.backend.music_store.repository.IProductRepository;
import dh.backend.music_store.service.ICategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {

    private final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    final ICategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ResponseDto<List<CategoryResponseDto>> findAll() {
        List<Category> categoriesDB = categoryRepository.findAll();
        List<CategoryResponseDto> categories = categoriesDB.stream().map(
                category -> modelMapper.map(category, CategoryResponseDto.class)).toList();
        ResponseDto<List<CategoryResponseDto>> responseDto = new  ResponseDto<List<CategoryResponseDto>>();
        responseDto.setData(categories);
        return responseDto;
    }

    @Override
    public CategoryResponseDto findById(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isEmpty()) {
            logger.error("Category {} NOT FOUND IN DB", id);
            throw new ResourceNotFoundException("Category " + id + " not found");
        }
        return modelMapper.map(category.get(), CategoryResponseDto.class);
    }


}
