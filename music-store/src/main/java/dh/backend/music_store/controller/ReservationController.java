package dh.backend.music_store.controller;

import dh.backend.music_store.dto.Generic.ResponseDto;
import dh.backend.music_store.dto.reservation.request.SaveReservationRequestDto;
import dh.backend.music_store.dto.reservation.response.ReservationByProductResponseDto;
import dh.backend.music_store.service.IReservationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationController {
    final IReservationService reservationService;
    public ReservationController(IReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations/products/{productId}")
    public ResponseEntity<ResponseDto<List<ReservationByProductResponseDto>>> getReservationsByProductId(@PathVariable Integer productId){
        ResponseDto<List<ReservationByProductResponseDto>> response = reservationService.getReservationsByProductId(productId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reservations/save")
    public ResponseEntity<ReservationByProductResponseDto> saveReservation(@Valid @RequestBody SaveReservationRequestDto saveReservationRequestDto){
        ReservationByProductResponseDto response = reservationService.save(saveReservationRequestDto);
        return ResponseEntity.ok(response);
    }
}
