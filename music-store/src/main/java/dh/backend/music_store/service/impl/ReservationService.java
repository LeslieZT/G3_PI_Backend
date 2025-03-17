package dh.backend.music_store.service.impl;

import dh.backend.music_store.dto.Generic.ResponseDto;
import dh.backend.music_store.dto.reservation.projection.ReservationByProductProjection;
import dh.backend.music_store.repository.IReservationRepository;
import dh.backend.music_store.service.IReservationService;
import dh.backend.music_store.dto.reservation.response.ReservationByProductResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService implements IReservationService {

    private final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    final IReservationRepository reservationRepository;

    public ReservationService(IReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public ResponseDto<List<ReservationByProductResponseDto>> getReservationsByProductId(Integer productId) {
        List<ReservationByProductProjection> reservationsDB = reservationRepository.findReservationsByProduct(productId);

        List<ReservationByProductResponseDto> data = reservationsDB.stream().map(projection -> new ReservationByProductResponseDto(
                projection.getId(),
                projection.getStartDate(),
                projection.getEndDate()
        )).toList();
        ResponseDto<List<ReservationByProductResponseDto>> response = new ResponseDto<>();
        response.setData(data);
        return response;
    }
}
