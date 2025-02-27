package com.dh.demo.dto.brand;

import com.dh.demo.entity.Brand;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BrandRegisterDto(
        @NotBlank
        @Valid
        String name,
        LocalDate creationDate
) {
}
