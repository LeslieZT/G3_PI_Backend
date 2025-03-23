package dh.backend.music_store.controller;

import dh.backend.music_store.dto.Generic.PaginationResponseDto;
import dh.backend.music_store.dto.Generic.RequestSearcherDto;
import dh.backend.music_store.dto.product.request.FindAllProductRequestDto;
import dh.backend.music_store.dto.product.request.SaveProductRequestDto;
import dh.backend.music_store.dto.product.request.UpdateProductRequestDto;
import dh.backend.music_store.dto.product.response.DetailProductResponseDto;
import dh.backend.music_store.dto.product.response.FindAllProductResponseDto;
import dh.backend.music_store.dto.product.response.FindOneProductResponseDto;
import dh.backend.music_store.dto.product.response.ResponseSearchProductDto;
import dh.backend.music_store.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductController {

    private IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/find-all")
    public ResponseEntity<PaginationResponseDto<FindAllProductResponseDto>>  findAll(@Valid @RequestBody(required = false) FindAllProductRequestDto request){
        PaginationResponseDto<FindAllProductResponseDto> response = productService.findAll(request);
        return ResponseEntity.ok(response);
    }
<<<<<<< HEAD
    @PostMapping("/save")
    public ResponseEntity<DetailProductResponseDto> save(@RequestBody SaveProductRequestDto saveProductRequestDto){
=======
    @PostMapping("/products/save")
    public ResponseEntity<DetailProductResponseDto> save(@Valid @RequestBody SaveProductRequestDto saveProductRequestDto){
>>>>>>> 3e7ae0d6b419aa199c3ffbe5cc4195959741bd0b
        DetailProductResponseDto detailResponse = productService.save(saveProductRequestDto);
        return ResponseEntity.ok(detailResponse);
    }
    //modificado para manejo de errores
    @GetMapping("/{id}")
    public ResponseEntity<DetailProductResponseDto> findDetailsById(@PathVariable Integer id) {
        DetailProductResponseDto product = productService.findDetailsById(id);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(product);
    }
    //añado
    @PostMapping("/{productId}/categories/{categoryId}")
    public ResponseEntity<DetailProductResponseDto> assignCategory(@PathVariable Integer productId, @PathVariable Integer categoryId) {
        DetailProductResponseDto updatedProduct = productService.assignCategoryToProduct(productId, categoryId);
        return ResponseEntity.ok(updatedProduct);
    }
    @PutMapping("/{id}")
    public ResponseEntity<DetailProductResponseDto> updateProduct(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateProductRequestDto updateProductRequestDto) {

        DetailProductResponseDto updatedProduct = productService.updateProduct(id, updateProductRequestDto);
        return ResponseEntity.ok(updatedProduct);
    }
    @PostMapping ("/products/search")
    public ResponseEntity<List<ResponseSearchProductDto>> searchProducts(@Valid @RequestBody RequestSearcherDto requestSearcherDto){
        List<ResponseSearchProductDto> responseDtos = productService.searchProducts(requestSearcherDto);
        return ResponseEntity.ok(responseDtos);
    }

    @PutMapping("/products/update")
    public ResponseEntity<?> update(@Valid @RequestBody UpdateProductRequestDto updateProductRequestDto){
        productService.update(updateProductRequestDto);
        String jsonResponse =  "{\"mensaje\" : \"El producto fue modificado\"}";
        return  ResponseEntity.ok(jsonResponse);
    }
}

