package com.dh.demo.dto.product;

import com.dh.demo.dto.brand.BrandResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


public record FindOneProductResponseDto(
        Integer id,
        String name,
        String description,
        Double pricePerHour,
        Integer stockQuantity,
        Boolean isAvailable,
        ProductCategoryResponseDto category,
        BrandResponseDto brand,
        List<ProductImageResponseDto> images
) {}
