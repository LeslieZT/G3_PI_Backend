package com.dh.demo.service.impl;


import com.dh.demo.dto.brand.BrandRegisterDto;
import com.dh.demo.dto.brand.BrandResponseDto;
import com.dh.demo.entity.Brand;
import com.dh.demo.repository.IBrandRepository;
import com.dh.demo.service.IBrandService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandService implements IBrandService {
    private final Logger logger = LoggerFactory.getLogger(BrandService.class);

    @Autowired
    private IBrandRepository brandRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<BrandResponseDto> showBrands() {
        return dataConversor(brandRepository.findAll());
    }

    public List<BrandResponseDto> dataConversor(List<Brand> brand){
        return brand.stream()
                .map(S -> new BrandResponseDto(S.getId(), S.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Brand saveBrand(BrandRegisterDto brandRegisterDto){
        Brand brand = brandRepository.save(new Brand(brandRegisterDto));
        return brand;
    }
}
