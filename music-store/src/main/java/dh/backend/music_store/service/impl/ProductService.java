package dh.backend.music_store.service.impl;


import dh.backend.music_store.dto.Generic.PaginationResponseDto;
import dh.backend.music_store.dto.Generic.RequestSearcherDto;
import dh.backend.music_store.dto.brand.BrandResponseDto;
import dh.backend.music_store.dto.category.CategoryResponseDto;
import dh.backend.music_store.dto.product.request.SaveProductRequestDto;
import dh.backend.music_store.dto.product.response.DetailProductResponseDto;
import dh.backend.music_store.dto.product.projection.FilteredProductProjection;
import dh.backend.music_store.dto.product.request.FindAllProductRequestDto;
import dh.backend.music_store.dto.product.response.FindAllProductResponseDto;
import dh.backend.music_store.dto.product.response.FindOneProductResponseDto;
import dh.backend.music_store.dto.product.response.ResponseSearchProductDto;
import dh.backend.music_store.entity.*;
import dh.backend.music_store.exception.BadRequestException;
import dh.backend.music_store.exception.ResourceNotFoundException;
import dh.backend.music_store.repository.IBrandRepository;
import dh.backend.music_store.repository.IProductRepository;
import dh.backend.music_store.repository.IReservationRepository;
import dh.backend.music_store.service.IBrandService;
import dh.backend.music_store.service.ICategoryService;
import dh.backend.music_store.service.IProductImageService;
import dh.backend.music_store.service.IProductService;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
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
    private IReservationRepository reservationRepository;


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
                projection.getPricePerHour(),
                projection.getCategoryName(),
                projection.getDescription()

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

    @Override
    public DetailProductResponseDto save(SaveProductRequestDto saveProductRequestDto) {
        logger.info("Ingresando al Service Producto | Guardar producto");
        DetailProductResponseDto detailProductResponseDto = null;
        List<Product> sameProductsByName = productRepository.findByName(saveProductRequestDto.getName());
        if(!sameProductsByName.isEmpty()){
            throw new BadRequestException("El nombre del producto ya se encuentra en uso");
        }

        logger.info("No existen productos con el mismo nombre, se procede al guardado");
        Product productToSave = new Product();
        logger.info("Buscando y mapeando categoria");
        Category category =  modelMapper.map(categoryService.findById(saveProductRequestDto.getCategoryId()), Category.class) ;
        Brand brand =  modelMapper.map(brandService.findById(saveProductRequestDto.getBrandId()), Brand.class);
        //seteo de la imagen a guardar
        List<ProductImage> images = new ArrayList<>();
        images.add(new ProductImage(null, productToSave, saveProductRequestDto.getImageUrl(), true));

        //seteo del producto a guardar
        productToSave.setName(saveProductRequestDto.getName());
        productToSave.setDescription(saveProductRequestDto.getDescription());
        productToSave.setPricePerHour(saveProductRequestDto.getPrice());
        productToSave.setStockQuantity(1);
        productToSave.setIsAvailable(true);
        productToSave.setCategory(category);
        productToSave.setImages(images);
        productToSave.setCreationDate(LocalDate.now());
        productToSave.setBrand(brand);
        productToSave.setModel(saveProductRequestDto.getModel());
        productToSave.setProduct_condition(saveProductRequestDto.getProductCondition());
        productToSave.setOrigin(saveProductRequestDto.getOrigin());
        productToSave.setLaunchYear(saveProductRequestDto.getLaunchYear());
        productToSave.setProduct_size(saveProductRequestDto.getSize());
        productToSave.setMaterial(saveProductRequestDto.getMaterial());
        productToSave.setRecommendedUse(saveProductRequestDto.getRecommendedUse());

        //guardado
        Product productSaved = productRepository.save(productToSave);
        logger.info("Producto persistido en la db");
        //mapeo response
        detailProductResponseDto = mapperToDetailProductResponseDto(productSaved);
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
        BrandResponseDto brandResponseDto = brandService.findById(product.getBrand().getId());
        //buscar imagenes no principales
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
    @Override
    public PaginationResponseDto<ResponseSearchProductDto> searchProducts(RequestSearcherDto requestSearcherDto,
                                                                          Pageable pageable) {

        Page<Product> productsPage;

        if (requestSearcherDto.getText() == null) {
            productsPage = productRepository.findAll(pageable);
        } else {
            productsPage = productRepository.searchProducts(requestSearcherDto.getText(), pageable);
        }

        // Filtrar los productos disponibles
        List<ResponseSearchProductDto> filteredProducts = productsPage.stream()
                .filter(product -> isProductAvaiable(product, requestSearcherDto.getDateInit(), requestSearcherDto.getDateEnd()))
                .map(product -> {
                    ResponseSearchProductDto productDto = modelMapper.map(product, ResponseSearchProductDto.class);
                    productDto.setBrand(product.getBrand().getName());
                    productDto.setCategory(product.getCategory().getName());

                    ProductImage productImage = productImageService.findByProductAndIsPrimary(product);
                    if (productImage != null) {
                        productDto.setImages(productImage.getUrl());
                    }

                    return productDto;
                }).toList();

        // Crear la nueva respuesta paginada con los datos filtrados
        PaginationResponseDto<ResponseSearchProductDto> paginationResponseDto = new PaginationResponseDto<>();
        paginationResponseDto.setData(filteredProducts); //
        paginationResponseDto.setPage(productsPage.getNumber()+1); //
        paginationResponseDto.setSize(productsPage.getSize());
        paginationResponseDto.setTotal(productsPage.getTotalPages());

        return paginationResponseDto;
    }

    private boolean  isProductAvaiable(Product product, LocalDate dateInit, LocalDate dateEnd){

        // Obtener todas las reservas activas del producto (que no sean RETURNED ni CANCELED)
        List<Reservation> activeReservations = reservationRepository.findByProductId(product.getId())
                .stream()
                .filter(reservation ->
                        reservation.getStatus() == ReservationStatus.PENDING ||
                                reservation.getStatus() == ReservationStatus.APPROVED ||
                                reservation.getStatus() == ReservationStatus.IN_PROGRESS
                )
                .toList();


        if(activeReservations.isEmpty() || dateInit ==null || dateEnd==null){
            return true;
        }else {
            // Verificar si alguna reserva se traslapa con el rango consultado
            boolean isAvailable = activeReservations.stream()
                    .noneMatch(reservation ->
                            dateEnd.isAfter(reservation.getStartDate()) && dateInit.isBefore(reservation.getEndDate())
                    );

            return isAvailable;}
    }

}
