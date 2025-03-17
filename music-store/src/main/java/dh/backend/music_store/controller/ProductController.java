package dh.backend.music_store.controller;

import dh.backend.music_store.dto.Generic.PaginationResponseDto;
import dh.backend.music_store.dto.product.request.FindAllProductRequestDto;
import dh.backend.music_store.dto.product.request.SaveProductRequestDto;
import dh.backend.music_store.dto.product.response.DetailProductResponseDto;
import dh.backend.music_store.dto.product.response.FindAllProductResponseDto;
import dh.backend.music_store.dto.product.response.FindOneProductResponseDto;
import dh.backend.music_store.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class ProductController {

    private IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products/find-all")
    public ResponseEntity<PaginationResponseDto<FindAllProductResponseDto>>  findAll(@Valid @RequestBody(required = false) FindAllProductRequestDto request){
        PaginationResponseDto<FindAllProductResponseDto> response = productService.findAll(request);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/products/save")
    public ResponseEntity<DetailProductResponseDto> save(@RequestBody SaveProductRequestDto saveProductRequestDto){
        DetailProductResponseDto detailResponse = productService.save(saveProductRequestDto);
        return ResponseEntity.ok(detailResponse);
    }
    @GetMapping("/products/{id}")
    public ResponseEntity<DetailProductResponseDto> findDetailsById(@PathVariable Integer id){
        DetailProductResponseDto detalleProductoResponseDto = productService.findDetailsById(id);
        return  ResponseEntity.ok(detalleProductoResponseDto);
    }
    //añado
    @PostMapping("/{productId}/categories/{categoryId}")
    public ResponseEntity<DetailProductResponseDto> assignCategory(@PathVariable Integer productId, @PathVariable Integer categoryId) {
        DetailProductResponseDto updatedProduct = productService.assignCategoryToProduct(productId, categoryId);
        return ResponseEntity.ok(updatedProduct);
    }
}
