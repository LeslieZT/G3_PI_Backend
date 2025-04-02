package dh.backend.music_store.service;

import dh.backend.music_store.dto.Generic.PaginationResponseDto;
import dh.backend.music_store.dto.Generic.RequestSearcherDto;
import dh.backend.music_store.dto.Generic.ResponseDto;
import dh.backend.music_store.dto.product.request.FindAllProductRequestDto;
import dh.backend.music_store.dto.product.request.SaveProductRequestDto;
import dh.backend.music_store.dto.product.request.UpdateProductRequestDto;
import dh.backend.music_store.dto.product.response.*;
import dh.backend.music_store.dto.reservation.projection.ReservationByProductProjection;
import dh.backend.music_store.dto.product.response.DetailProductResponseDto;
import dh.backend.music_store.dto.product.response.FindOneProductResponseDto;
import dh.backend.music_store.dto.product.response.ResponseSearchProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface IProductService {
    PaginationResponseDto<FindAllProductResponseDto> findAll(FindAllProductRequestDto request);
    FindOneProductResponseDto findOne(Integer id);
    DetailProductResponseDto findDetailsById(Integer id);
    DetailProductResponseDto save(SaveProductRequestDto saveProductRequestDto);
    void update(UpdateProductRequestDto updateProductRequestDto);

    boolean productIsAvailable(LocalDate startDate, LocalDate endDate, List<ReservationByProductProjection> reservations);

    ResponseDto<DeleteProductResponseDto> delete(Integer id);

    List<ResponseSearchProductDto> searchProducts(RequestSearcherDto requestSearcherDto);

}
