package dh.backend.music_store.controller;

import dh.backend.music_store.dto.Generic.PaginationResponseDto;
import dh.backend.music_store.dto.product.*;
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

    @PostMapping("/products")
    public ResponseEntity<PaginationResponseDto<FindAllProductResponseDto>>  findAll(@Valid @RequestBody(required = false) FindAllProductRequestDto request){
        PaginationResponseDto<FindAllProductResponseDto> response = productService.findAll(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products/{id}")
    public  ResponseEntity<FindOneProductResponseDto> findOne(@PathVariable Integer id){
        FindOneProductResponseDto product = productService.findOne(id);
        return ResponseEntity.ok(product);

    }

    @GetMapping("/producto/detalle/{id}")
    public ResponseEntity<DetalleProductoResponseDto> buscarDetallesPorId(@PathVariable Integer id){
        DetalleProductoResponseDto detalleProductoResponseDto = productService.buscarDetallesPorId(id);
        if(detalleProductoResponseDto != null){
            return ResponseEntity.ok(detalleProductoResponseDto);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
