package com.dh.demo.controller;

import com.dh.demo.dto.Generic.ResponseDto;
import com.dh.demo.dto.brand.BrandRegisterDto;
import com.dh.demo.dto.brand.BrandResponseDto;
import com.dh.demo.entity.Brand;
import com.dh.demo.service.IBrandService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class BrandController {
    @Autowired
    private IBrandService brandService;

    @GetMapping("/brands") //Listar todos los brands
    public ResponseEntity<List<BrandResponseDto>> getAllBrands(){
        List<BrandResponseDto> brands = brandService.showBrands();
        return ResponseEntity.ok(brands);
    }

    @PostMapping
    public ResponseEntity registerBrand(@RequestBody @Valid BrandRegisterDto brandRegisterDto, UriComponentsBuilder uriComponentsBuilder){
        Brand brand = brandService.saveBrand(brandRegisterDto);
        BrandResponseDto brandResponseDto = new BrandResponseDto(brand);
        URI url = uriComponentsBuilder.path("/brands/{id}").buildAndExpand(brand.getId()).toUri();
        return ResponseEntity.created(url).body(brandResponseDto);
    }
}
