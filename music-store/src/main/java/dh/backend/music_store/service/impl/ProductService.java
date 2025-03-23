
package dh.backend.music_store.service.impl;


import dh.backend.music_store.dto.Generic.PaginationResponseDto;
<<<<<<< HEAD
import dh.backend.music_store.dto.Generic.ResponseDto;
import dh.backend.music_store.dto.auth.response.ProductResponseDto;
=======
import dh.backend.music_store.dto.Generic.RequestSearcherDto;
>>>>>>> 3e7ae0d6b419aa199c3ffbe5cc4195959741bd0b
import dh.backend.music_store.dto.brand.BrandResponseDto;
import dh.backend.music_store.dto.category.CategoryResponseDto;
import dh.backend.music_store.dto.product.request.SaveProductRequestDto;
import dh.backend.music_store.dto.product.request.UpdateProductRequestDto;
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
import dh.backend.music_store.repository.ICategoryRepository;
import dh.backend.music_store.repository.IProductRepository;
import dh.backend.music_store.repository.IReservationRepository;
import dh.backend.music_store.service.IBrandService;
import dh.backend.music_store.service.ICategoryService;
import dh.backend.music_store.service.IProductImageService;
import dh.backend.music_store.service.IProductService;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class ProductService implements IProductService {
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    final IProductRepository productRepository;
    private final ICategoryRepository categoryRepository;
    private ICategoryService categoryService;
    private IProductImageService productImageService;
    private IBrandService brandService;
    @Autowired
    private IReservationRepository reservationRepository;


    @Autowired
    private ModelMapper modelMapper;

    public ProductService(IProductRepository productRepository, ICategoryService categoryService,
                          IProductImageService productImageService, IBrandService brandService,
                          ICategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.productImageService = productImageService;
        this.brandService = brandService;
        this.categoryRepository = categoryRepository;
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
        Optional<Product> sameProductsByName = productRepository.findByName(saveProductRequestDto.getName());
        if(!sameProductsByName.isPresent()){
            throw new BadRequestException("El nombre del producto ya se encuentra en uso");
        }

        logger.info("No existen productos con el mismo nombre, se procede al guardado");
        Product productToSave = new Product();
        logger.info("Buscando y mapeando categoria");
        List<Category> categories = saveProductRequestDto.getCategoryIds().stream()
                .map(categoryId -> modelMapper.map(categoryService.findById(categoryId), Category.class))
                .collect(Collectors.toList());
        Brand brand =  modelMapper.map(brandService.findById(saveProductRequestDto.getBrandId()), Brand.class);
        //seteo de la imagen a guardar
        List<ProductImage> images = new ArrayList<>();
        images.add(new ProductImage(null, productToSave, saveProductRequestDto.getImageUrl(), true));

        //seteo del producto a guardar
        productToSave.setName(saveProductRequestDto.getName());
        productToSave.setDescription(saveProductRequestDto.getDescription());
        productToSave.setPrice(saveProductRequestDto.getPrice());
        productToSave.setStockQuantity(1);
        productToSave.setIsAvailable(true);
        productToSave.setCategories(categories);
        productToSave.setImages(images);
        productToSave.setCreationDate(LocalDate.now());
        productToSave.setBrand(brand);
        productToSave.setModel(saveProductRequestDto.getModel());
        productToSave.setProductCondition(saveProductRequestDto.getProductCondition());
        productToSave.setOrigin(saveProductRequestDto.getOrigin());
        productToSave.setLaunchYear(saveProductRequestDto.getLaunchYear());
        productToSave.setSize(saveProductRequestDto.getSize());
        productToSave.setMaterial(saveProductRequestDto.getMaterial());
        productToSave.setRecommendedUse(saveProductRequestDto.getRecommendedUse());

        //guardado
        Product productSaved = productRepository.save(productToSave);
        logger.info("Producto persistido en la db");
        //mapeo response
        detailProductResponseDto = mapperToDetailProductResponseDto(productSaved);
        return detailProductResponseDto;
    }

    @Override
<<<<<<< HEAD
    public DetailProductResponseDto assignCategoryToProduct(Integer productId, Integer categoryId) {
        logger.info("Asignando categoría {} al producto {}", categoryId, productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría con ID " + categoryId + " no encontrada"));

        Category category = categoryService.findEntityById(categoryId);

        if (product.getCategories() == null) {
            product.setCategories(new ArrayList<>());
        }

        if (!product.getCategories().contains(category)) {
            product.getCategories().add(category);
            productRepository.save(product);
            logger.info("Categoría asignada con éxito");
        } else {
            logger.warn("El producto ya tiene esta categoría asignada");
        }

        return mapperToDetailProductResponseDto(product);
    }



    @Override
    public ResponseDto<List<ProductResponseDto>> findAll() {
        List<Product> productsDB = productRepository.findAll();
        List<ProductResponseDto> products = productsDB.stream()
                .map(product -> modelMapper.map(product, ProductResponseDto.class))
                .collect(Collectors.toList());

        ResponseDto<List<ProductResponseDto>> responseDto = new ResponseDto<>();
        responseDto.setData(products);
        return responseDto;
    }

    @Override
    public ProductResponseDto findById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Product {} NOT FOUND IN DB", id);
                    return new ResourceNotFoundException("Product " + id + " not found");
                });

        return modelMapper.map(product, ProductResponseDto.class);
    }

    //*
    private ProductResponseDto mapToDto(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .pricePerHour(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .isAvailable(product.getIsAvailable())
                .brandName(product.getBrandId().getName()) // Si `Brand` tiene un `name`
                .model(product.getModel())
                .productCondition(product.getProductCondition())
                .origin(product.getOrigin())
                .launchYear(product.getLaunchYear())
                .productSize(product.getSize())
                .material(product.getMaterial())
                .recommendedUse(product.getRecommendedUse())
                .categoryNames(product.getCategories().stream().map(Category::getName).toList()) // Convertimos categorías a nombres
                .imageUrls(product.getImages().stream().map(ProductImage::getImageUrl).toList()) // Convertimos imágenes a URLs
                .build();
    }

    @Override
    public Product getProductByName(String name) {
        return productRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + name));
    }

    @Override
    public DetailProductResponseDto updateProduct(Integer productId, UpdateProductRequestDto updateProductRequestDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        // Actualizar campos básicos del producto
        product.setName(updateProductRequestDto.getName());
        product.setPrice(updateProductRequestDto.getPrice());
        product.setDescription(updateProductRequestDto.getDescription());
        product.setModel(updateProductRequestDto.getModel());
        product.setProductCondition(updateProductRequestDto.getProductCondition());
        product.setOrigin(updateProductRequestDto.getOrigin());
        product.setLaunchYear(updateProductRequestDto.getLaunchYear());
        product.setMaterial(updateProductRequestDto.getMaterial());
        product.setSize(updateProductRequestDto.getSize());
        product.setRecommendedUse(updateProductRequestDto.getRecommendedUse());


        // Limpiar imágenes existentes
        product.getImages().clear();

        if (updateProductRequestDto.getImageUrls() != null && !updateProductRequestDto.getImageUrls().isEmpty()) {
            List<ProductImage> updatedImages = new ArrayList<>();

            for (int i = 0; i < updateProductRequestDto.getImageUrls().size(); i++) {
                String url = updateProductRequestDto.getImageUrls().get(i);
                ProductImage image = new ProductImage();
                image.setImageUrl(url);
                image.setProduct(product);
                image.setIsPrimary(i == 0); // Primera imagen como principal
                updatedImages.add(image);
            }

            product.getImages().addAll(updatedImages);
        }

        productRepository.save(product);

        // Construir la respuesta DTO
        List<String> categories = product.getCategories().stream()
                .map(Category::getName)
                .toList();

        List<String> secondaryImages = product.getImages().stream()
                .filter(image -> !image.getIsPrimary())
                .map(ProductImage::getImageUrl)
                .toList();

        String mainImage = product.getImages().stream()
                .filter(ProductImage::getIsPrimary)
                .map(ProductImage::getImageUrl)
                .findFirst()
                .orElse(null);

        return new DetailProductResponseDto(
                product.getId(),
                categories,
                product.getName(),
                mainImage,
                product.getDescription(),
                product.getPrice(),
                product.getBrandId().getName(), // Asegúrate que Brand tiene un método getName()
                product.getModel(),
                product.getProductCondition(),
                product.getOrigin(),
                product.getLaunchYear(),
                product.getSize(),
                product.getMaterial(),
                product.getRecommendedUse(),
                secondaryImages
        );
    }


    @Override
    public List<ProductResponseDto> findByCategory(String categoryName) {
        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada: " + categoryName));

        List<Product> products = productRepository.findByCategory_Name(categoryName);

        return products.stream()
                .map(product -> modelMapper.map(product, ProductResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDto> findByCategoryId(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + categoryId));

        List<Product> products = productRepository.findByCategory_Id(categoryId);

        return products.stream()
                .map(product -> modelMapper.map(product, ProductResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDto> filterProducts(String search, List<Integer> categoryIds, boolean hasCategories, int limit, int offset) {
        List<FilteredProductProjection> products = productRepository.filterProducts(search, categoryIds, hasCategories, limit, offset);

        return products.stream()
                .map(product -> modelMapper.map(product, ProductResponseDto.class))
                .collect(Collectors.toList());
    }

=======
    public void update(UpdateProductRequestDto updateProductRequestDto){
        logger.info("Ingresando al Service Producto | Modificar producto");
        //buscando si existe para update
        Product productToSave = productRepository.findById(updateProductRequestDto.getId())
                .orElseThrow(()-> new ResourceNotFoundException("Producto no encontrado"));
        //regla de negocio de no nombres repetidos
        List<Product> sameProductsByName = productRepository.findByName(updateProductRequestDto.getName());
        if(!sameProductsByName.isEmpty()){
            throw new BadRequestException("No es posible modificar, el nombre nuevo del producto ya se encuentra en uso");
        }
        logger.info("No existen productos con el mismo nombre, se procede al guardado");

        logger.info("Buscando y mapeando categoria");
        Category category =  modelMapper.map(categoryService.findById(updateProductRequestDto.getCategoryId()), Category.class) ;
        Brand brand =  modelMapper.map(brandService.findById(updateProductRequestDto.getBrandId()), Brand.class);
        //seteo de la imagen a modificar
        List<ProductImage> images = productToSave.getImages();
        images.get(0).setUrl(updateProductRequestDto.getImageUrl());

        //seteo del producto a modificar
        productToSave.setId(updateProductRequestDto.getId());
        productToSave.setName(updateProductRequestDto.getName());
        productToSave.setDescription(updateProductRequestDto.getDescription());
        productToSave.setPricePerHour(updateProductRequestDto.getPrice());
        productToSave.setStockQuantity(1);
        productToSave.setIsAvailable(true);
        productToSave.setCategory(category);
        //productToSave.setImages(images);
        productToSave.setCreationDate(LocalDate.now());
        productToSave.setBrand(brand);
        productToSave.setModel(updateProductRequestDto.getModel());
        productToSave.setProduct_condition(updateProductRequestDto.getProductCondition());
        productToSave.setOrigin(updateProductRequestDto.getOrigin());
        productToSave.setLaunchYear(updateProductRequestDto.getLaunchYear());
        productToSave.setProduct_size(updateProductRequestDto.getSize());
        productToSave.setMaterial(updateProductRequestDto.getMaterial());
        productToSave.setRecommendedUse(updateProductRequestDto.getRecommendedUse());

        //guardado
        productRepository.save(productToSave);
        logger.info("Producto modificado en la db");
    }


>>>>>>> 3e7ae0d6b419aa199c3ffbe5cc4195959741bd0b

    //funcion de mapeo a DetailProductResponseDto
    private DetailProductResponseDto mapperToDetailProductResponseDto(Product product){
        logger.info("Mapeando producto a response Detalle");
        //buscar categoria del producto
        // Obtener nombres de todas las categorías del producto
        List<String> categoryNames = product.getCategories().stream()
                .map(category -> category.getName())
                .collect(Collectors.toList());

        logger.info("Categorías encontradas: " + categoryNames);
        //buscar imagen principal del producto
        ProductImage productImage = productImageService.findByProductAndIsPrimary(product);
        String url = "//url.com";
        if(productImage != null){
            logger.info("Imagen principal producto encontrada");
            url = productImage.getImageUrl();
        }
        //buscar marca del producto
        BrandResponseDto brandResponseDto = brandService.findById(product.getBrand().getId());
        //buscar imagenes no principales
        List<ProductImage> secondaryImagesFromDb = productImageService.findByProductAndIsNotPrimary(product);
        List<String> secondaryImages = new ArrayList<>();
        for (ProductImage pi : secondaryImagesFromDb){
            secondaryImages.add(pi.getImageUrl());
        }
        //mapeo
        DetailProductResponseDto detailProductResponseDto = new DetailProductResponseDto(product.getId(),
                categoryNames,
                product.getName(),
                url,
                product.getDescription(),
                product.getPrice(),
                brandResponseDto.getName(),
                product.getModel(),
                product.getProductCondition(),
                product.getOrigin(),
                product.getLaunchYear(),
                product.getSize(),
                product.getMaterial(),
                product.getRecommendedUse(),
                secondaryImages);
        return detailProductResponseDto;
    }
    @Override
    public List<ResponseSearchProductDto> searchProducts(RequestSearcherDto requestSearcherDto) {
        List<Product> products;
        //FALTA FILTRADO SOLO DATE
        if(requestSearcherDto.getText() == null){
            products = productRepository.findAll();
        }else{
            products = productRepository.searchProducts(requestSearcherDto.getText());
        }
    List<ResponseSearchProductDto> productResponseDtos =new ArrayList<>();

        for (Product product : products){

        ResponseSearchProductDto productft = modelMapper.map(product,ResponseSearchProductDto.class);
        productft.setBrand(product.getBrand().getName());
        productft.setCategory(product.getCategory().getName());
        ProductImage productImage = productImageService.findByProductAndIsPrimary(product);
        productft.setImages(productImage.getUrl());
        //FILTRANDO DISPONIBILIDAD POR FECHAS
        if(isProductAvaiable(product, requestSearcherDto.getDateInit(), requestSearcherDto.getDateEnd())){
            productResponseDtos.add(productft);
        }
    }

        return productResponseDtos;
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
