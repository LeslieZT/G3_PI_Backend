package com.dh.demo.dto.brand;

import com.dh.demo.entity.Brand;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BrandResponseDto (
         Integer id,
         String name
){
    public BrandResponseDto(Brand brand) {
        this(brand.getId(), brand.getName());
    }
}
