package dh.backend.music_store.service.impl;

import dh.backend.music_store.dto.Generic.ResponseDto;

import dh.backend.music_store.dto.brand.BrandResponseDto;
import dh.backend.music_store.entity.Brand;
import dh.backend.music_store.exception.ResourceNotFoundException;
import dh.backend.music_store.repository.IBrandRepository;
import dh.backend.music_store.service.IBrandService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService implements IBrandService {

    private final Logger logger = LoggerFactory.getLogger(BrandService.class);

    final IBrandRepository BrandRepository;

    @Autowired
    private ModelMapper modelMapper;

    public BrandService(IBrandRepository BrandRepository) {
        this.BrandRepository = BrandRepository;
    }

    @Override
    public ResponseDto<List<BrandResponseDto>> findAll() {
        List<Brand> brandsDB = BrandRepository.findAll();
        List<BrandResponseDto> brands = brandsDB.stream().map(
                Brand -> modelMapper.map(Brand, BrandResponseDto.class)).toList();
        ResponseDto<List<BrandResponseDto>> responseDto = new  ResponseDto<List<BrandResponseDto>>();
        responseDto.setData(brands);
        return responseDto;
    }

    @Override
    public BrandResponseDto findById(Integer id) {
       Optional<Brand> brandFromDb =  BrandRepository.findById(id);
       if(brandFromDb.isEmpty()){
           logger.error("Brand(Marca) {} not found", id);
           throw new ResourceNotFoundException("Marca " + id + " not found");
       }
       return modelMapper.map(brandFromDb, BrandResponseDto.class);
    }
}