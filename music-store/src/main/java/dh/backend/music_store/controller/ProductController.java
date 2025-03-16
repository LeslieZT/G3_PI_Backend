package dh.backend.music_store.controller;

import dh.backend.music_store.dto.Generic.PaginationResponseDto;
import dh.backend.music_store.dto.Generic.RequestSearcherDto;
import dh.backend.music_store.dto.product.request.FindAllProductRequestDto;
import dh.backend.music_store.dto.product.response.DetailProductResponseDto;
import dh.backend.music_store.dto.product.response.FindAllProductResponseDto;
import dh.backend.music_store.dto.product.response.FindOneProductResponseDto;
import dh.backend.music_store.dto.product.response.ResponseSearchProductDto;
import dh.backend.music_store.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ProductController {
    @Autowired
    IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products/find-all")
    public ResponseEntity<PaginationResponseDto<FindAllProductResponseDto>>  findAll(@Valid @RequestBody(required = false) FindAllProductRequestDto request){
        PaginationResponseDto<FindAllProductResponseDto> response = productService.findAll(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<DetailProductResponseDto> findDetailsById(@PathVariable Integer id){
        DetailProductResponseDto detalleProductoResponseDto = productService.findDetailsById(id);
        return  ResponseEntity.ok(detalleProductoResponseDto);
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<ResponseSearchProductDto>> searchProducts(@RequestBody RequestSearcherDto requestSearcherDto){
        List<ResponseSearchProductDto> responseDtos = productService.searchProducts(requestSearcherDto);
        return ResponseEntity.ok(responseDtos);
    }
}
