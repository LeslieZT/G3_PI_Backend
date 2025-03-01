package dh.backend.music_store.service.impl;


import dh.backend.music_store.dto.Generic.PaginationResponseDto;
import dh.backend.music_store.dto.product.DetalleProductoResponseDto;
import dh.backend.music_store.dto.product.FindAllProductRequestDto;
import dh.backend.music_store.dto.product.FindAllProductResponseDto;
import dh.backend.music_store.dto.product.FindOneProductResponseDto;

import dh.backend.music_store.entity.Category;
import dh.backend.music_store.entity.Product;
import dh.backend.music_store.entity.ProductImage;
import dh.backend.music_store.exception.ResourceNotFoundException;
import dh.backend.music_store.repository.IProductRepository;
import dh.backend.music_store.service.ICategoryService;
import dh.backend.music_store.service.IProductImageService;
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
    private ICategoryService categoryService;
    private IProductImageService productImageService;


    @Autowired
    private ModelMapper modelMapper;

    public ProductService(IProductRepository productRepository, ICategoryService categoryService, IProductImageService productImageService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.productImageService = productImageService;
    }

    @Override
    public PaginationResponseDto<FindAllProductResponseDto> findAll(FindAllProductRequestDto request) {
        if (request == null) {
            request = new FindAllProductRequestDto();
        }
        int offset = (request.getPage() - 1) * request.getLimit();

        List<Object[]> productsDB = this.productRepository.filterProducts(request.getSearch(), request.getCategoryId(), request.getLimit(), offset);
        Integer totalDB = this.productRepository.countFilterProducts(request.getSearch(), request.getCategoryId());

        List<FindAllProductResponseDto> data = productsDB.stream().map(obj -> new FindAllProductResponseDto(
                (Integer) obj[0],  // id
                (String) obj[1],   // name
                (obj[2] != null) ? (String) obj[2] : null,
                (Double) obj[3]   // pricePerHour */
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
    public DetalleProductoResponseDto buscarDetallesPorId(Integer id) {
        logger.info("Ingresando al Service Producto | Buscar Detalles por id");
        Optional<Product> productoDesdeDb = productRepository.findById(id);
        DetalleProductoResponseDto detalleProductoResponseDto = null;
        if(productoDesdeDb.isPresent()){
            logger.info("Producto encontrado en la db");
            detalleProductoResponseDto = mapperToDetalleProductoResponseDto(productoDesdeDb.get());
            logger.info("Producto mapeado a Response Detalle");
        }
        return detalleProductoResponseDto;
    }


    //funcion de mapeo a DetalleProductoResponseDto
    private DetalleProductoResponseDto mapperToDetalleProductoResponseDto(Product product){
        logger.info("Mapeando producto A response Detalle");
        //buscar categoria del producto
        Category category = categoryService.buscarPorId(product.getCategory().getId()).get();
        logger.info("Categoria encontrada");
        //buscar imagen principal del producto
        ProductImage productImage = productImageService.encontrarImagenPrincipalProducto(product);
        String url = "//url.com";
        if(productImage != null){
            logger.info("Imagen principal producto encontrada");
            url = productImage.getUrl();
        }

        //mapeando
        DetalleProductoResponseDto detalleProductoResponseDto = new DetalleProductoResponseDto(product.getId(),
                category.getName(),
                product.getName(),
                url,
                product.getDescription(),
                product.getPricePerHour(),
                product.getMarca(),
                product.getModelo(),
                product.getCondicion(),
                product.getOrigen(),
                product.getAnioLanzamiento(),
                product.getMedidas(),
                product.getMaterial(),
                product.getUsoRecomendado());
        return detalleProductoResponseDto;
    }

}
