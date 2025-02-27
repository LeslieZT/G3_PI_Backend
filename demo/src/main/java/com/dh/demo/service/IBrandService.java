package com.dh.demo.service;

import com.dh.demo.dto.Generic.ResponseDto;
import com.dh.demo.dto.brand.BrandRegisterDto;
import com.dh.demo.dto.brand.BrandResponseDto;
import com.dh.demo.entity.Brand;

import java.util.List;

public interface IBrandService {
    List<BrandResponseDto> showBrands();

    Brand saveBrand(BrandRegisterDto brandRegisterDto);
}
