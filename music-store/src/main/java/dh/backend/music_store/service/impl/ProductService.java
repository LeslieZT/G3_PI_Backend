package dh.backend.music_store.service.impl;


import dh.backend.music_store.dto.Generic.PaginationResponseDto;
import dh.backend.music_store.dto.product.projection.FilteredProductProjection;
import dh.backend.music_store.dto.product.request.FindAllProductRequestDto;
import dh.backend.music_store.dto.product.response.FindAllProductResponseDto;
import dh.backend.music_store.dto.product.response.FindOneProductResponseDto;

import dh.backend.music_store.entity.Product;
import dh.backend.music_store.exception.ResourceNotFoundException;
import dh.backend.music_store.repository.IProductRepository;
import dh.backend.music_store.service.IProductService;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    final IProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public PaginationResponseDto<FindAllProductResponseDto> findAll(FindAllProductRequestDto request) {
        if (request == null) {
            request = new FindAllProductRequestDto();
        }
        int offset = (request.getPage() - 1) * request.getLimit();

        logger.info("Request: {}", request);

        boolean hasCategories = request.getCategories() != null && !request.getCategories().isEmpty();

        logger.info("HasCategories: {}", hasCategories);

        List<FilteredProductProjection> productsDB = this.productRepository.filterProducts(request.getSearch(), request.getCategories(), hasCategories,  request.getLimit(), offset);
        Integer totalDB = this.productRepository.countFilterProducts(request.getSearch(), request.getCategories(), hasCategories);

        logger.info("ProductsDB: {}", productsDB);

        List<FindAllProductResponseDto> data = productsDB.stream().map(projection -> new FindAllProductResponseDto(
                projection.getId(),
                projection.getName(),
                projection.getUrl(),
                projection.getPricePerHour()

        )).toList();

        PaginationResponseDto<FindAllProductResponseDto> paginationResponse = new PaginationResponseDto<FindAllProductResponseDto>();
        paginationResponse.setData(data);
        paginationResponse.setPage(request.getPage());
        paginationResponse.setSize(request.getLimit());
        paginationResponse.setTotal(totalDB);
        return paginationResponse;
    }

    @Override
    public FindOneProductResponseDto findOne(Integer id) {
        Optional<Product> producto = this.productRepository.findById(id);
        if(producto.isEmpty()) {
            logger.error("Producto {} not found", id);
            throw new ResourceNotFoundException("Producto " + id + " not found");
        }
        FindOneProductResponseDto responseProduct = modelMapper.map(producto.get(), FindOneProductResponseDto.class);
        return responseProduct;

    }
}
