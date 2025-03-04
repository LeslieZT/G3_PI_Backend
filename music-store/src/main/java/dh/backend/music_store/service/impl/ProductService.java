package dh.backend.music_store.service.impl;


import dh.backend.music_store.dto.Generic.PaginationResponseDto;
import dh.backend.music_store.dto.brand.BrandResponseDto;
import dh.backend.music_store.dto.category.CategoryResponseDto;
import dh.backend.music_store.dto.product.response.DetailProductResponseDto;
import dh.backend.music_store.dto.product.projection.FilteredProductProjection;
import dh.backend.music_store.dto.product.request.FindAllProductRequestDto;
import dh.backend.music_store.dto.product.response.FindAllProductResponseDto;
import dh.backend.music_store.dto.product.response.FindOneProductResponseDto;
import dh.backend.music_store.entity.Product;
import dh.backend.music_store.entity.ProductImage;
import dh.backend.music_store.exception.ResourceNotFoundException;
import dh.backend.music_store.repository.IProductRepository;
import dh.backend.music_store.service.IBrandService;
import dh.backend.music_store.service.ICategoryService;
import dh.backend.music_store.service.IProductImageService;
import dh.backend.music_store.service.IProductService;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    final IProductRepository productRepository;
    private ICategoryService categoryService;
    private IProductImageService productImageService;
    private IBrandService brandService;


    @Autowired
    private ModelMapper modelMapper;

    public ProductService(IProductRepository productRepository, ICategoryService categoryService, IProductImageService productImageService, IBrandService brandService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.productImageService = productImageService;
        this.brandService = brandService;
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

    @Override
    public DetailProductResponseDto findDetailsById(Integer id) {
        logger.info("Ingresando al Service Producto | Buscar Detalles por id");
        Optional<Product> productFromDb = productRepository.findById(id);
        DetailProductResponseDto detailProductResponseDto = null;
        if(productFromDb.isEmpty()){
            logger.error("Producto no encontrado en la db");
            throw new ResourceNotFoundException("Producto " + id + " not found");
        }
        detailProductResponseDto = mapperToDetailProductResponseDto(productFromDb.get());
        logger.info("Producto mapeado a Response Detalle");
        return detailProductResponseDto;
    }


    //funcion de mapeo a DetailProductResponseDto
    private DetailProductResponseDto mapperToDetailProductResponseDto(Product product){
        logger.info("Mapeando producto a response Detalle");
        //buscar categoria del producto
        CategoryResponseDto category = categoryService.findById(product.getCategory().getId());
        logger.info("Categoria encontrada");
        //buscar imagen principal del producto
        ProductImage productImage = productImageService.findByProductAndIsPrimary(product);
        String url = "//url.com";
        if(productImage != null){
            logger.info("Imagen principal producto encontrada");
            url = productImage.getUrl();
        }
        //buscar marca del producto
        BrandResponseDto brandResponseDto = brandService.findById(product.getBrandId().getId());
        //buscaar imagenes no principales
        List<ProductImage> secondaryImagesFromDb = productImageService.findByProductAndIsNotPrimary(product);
        List<String> secondaryImages = new ArrayList<>();
        for (ProductImage pi : secondaryImagesFromDb){
            secondaryImages.add(pi.getUrl());
        }
        //mapeo
        DetailProductResponseDto detailProductResponseDto = new DetailProductResponseDto(product.getId(),
                category.getName(),
                product.getName(),
                url,
                product.getDescription(),
                product.getPricePerHour(),
                brandResponseDto.getName(),
                product.getModel(),
                product.getProduct_condition(),
                product.getOrigin(),
                product.getLaunchYear(),
                product.getProduct_size(),
                product.getMaterial(),
                product.getRecommendedUse(),
                secondaryImages);
        return detailProductResponseDto;
    }

}
