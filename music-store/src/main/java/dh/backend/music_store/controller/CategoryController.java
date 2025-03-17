package dh.backend.music_store.controller;


import dh.backend.music_store.dto.Generic.ResponseDto;
import dh.backend.music_store.dto.category.CategoryResponseDto;
import dh.backend.music_store.entity.Category;
import dh.backend.music_store.service.ICategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    private ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ResponseEntity<ResponseDto<List<CategoryResponseDto>>> findAll(){
        ResponseDto<List<CategoryResponseDto>> response = categoryService.findAll();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{name}")
    public ResponseEntity<Category> getCategoryByName(@PathVariable String name) {
        return ResponseEntity.ok(categoryService.getCategoryByName(name));
    }
}
