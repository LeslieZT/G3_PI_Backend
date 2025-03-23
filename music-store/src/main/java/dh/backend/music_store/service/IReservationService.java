package dh.backend.music_store.service;

import dh.backend.music_store.dto.Generic.ResponseDto;
import dh.backend.music_store.dto.reservation.response.ReservationByProductResponseDto;

import java.util.List;

public interface IReservationService {
    ResponseDto<List<ReservationByProductResponseDto>> getReservationsByProductId(Integer productId);
}
