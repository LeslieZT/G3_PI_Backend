package dh.backend.music_store.controller;

import dh.backend.music_store.dto.Generic.ResponseDto;

import dh.backend.music_store.dto.brand.BrandResponseDto;
import dh.backend.music_store.service.IBrandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BrandController {

    private IBrandService brandService;

    public BrandController(IBrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/brands")
    public ResponseEntity<ResponseDto<List<BrandResponseDto>>> findAll(){
        ResponseDto<List<BrandResponseDto>> response = brandService.findAll();
        return ResponseEntity.ok(response);
    }
}
