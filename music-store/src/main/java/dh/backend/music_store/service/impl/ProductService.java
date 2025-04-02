package dh.backend.music_store.service.impl;


import dh.backend.music_store.dto.Generic.PaginationResponseDto;
import dh.backend.music_store.dto.Generic.RequestSearcherDto;
import dh.backend.music_store.dto.Generic.ResponseDto;
import dh.backend.music_store.dto.brand.BrandResponseDto;
import dh.backend.music_store.dto.category.CategoryResponseDto;
import dh.backend.music_store.dto.product.request.SaveProductRequestDto;
import dh.backend.music_store.dto.product.request.UpdateProductRequestDto;
import dh.backend.music_store.dto.product.response.*;
import dh.backend.music_store.dto.product.projection.FilteredProductProjection;
import dh.backend.music_store.dto.product.request.FindAllProductRequestDto;
import dh.backend.music_store.dto.reservation.projection.ReservationByProductProjection;
import dh.backend.music_store.dto.user.response.ChangeRoleResponseDto;
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

    @Override
    public void update(UpdateProductRequestDto updateProductRequestDto){
        logger.info("Ingresando al Service Producto | Modificar producto");
        //buscando si existe para update
        Product productToSave = productRepository.findById(updateProductRequestDto.getId())
                .orElseThrow(()-> new ResourceNotFoundException("Producto no encontrado"));
        //regla de negocio de no nombres repetidos
        List<Product> sameProductsByName = productRepository.findByName(updateProductRequestDto.getName());
        if(!sameProductsByName.isEmpty()){
            Integer idEqualProduct  = sameProductsByName.get(0).getId();
            // si es el mismo producto se puede mantener el nombre
            if(!idEqualProduct.equals(updateProductRequestDto.getId())){
                throw new BadRequestException("No es posible modificar, el nombre nuevo del producto ya se encuentra en uso");
            }
        }
        logger.info("No existen productos distintos con el mismo nombre, se procede al guardado");

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

    @Override
    //ahi falta coordinar con waldir un poco la diferencia de logica
    public boolean productIsAvailable(LocalDate startDate, LocalDate endDate, List<ReservationByProductProjection> reservations){
        logger.info("Validando Disponibilidad de reserva...");
        return reservations.stream()
                .noneMatch(existingReservation ->
                        !(endDate.isBefore(existingReservation.getStartDate()) ||
                                startDate.isAfter(existingReservation.getEndDate()))
                );
    }

    @Override
    public ResponseDto<DeleteProductResponseDto> delete(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()){
            throw new ResourceNotFoundException("Product not found");
        }
        productRepository.deleteById(id);
        ResponseDto<DeleteProductResponseDto> response = new ResponseDto<>();
        response.setData(new DeleteProductResponseDto("Product deleted successfully"));
        return response;
    }

}
