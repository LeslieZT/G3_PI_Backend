package dh.backend.music_store.service.impl;

import dh.backend.music_store.dto.Generic.ResponseDto;
import dh.backend.music_store.dto.product.response.DetailProductResponseDto;
import dh.backend.music_store.dto.product.response.FindOneProductResponseDto;
import dh.backend.music_store.dto.reservation.projection.ReservationByProductProjection;
import dh.backend.music_store.dto.reservation.request.SaveReservationRequestDto;
import dh.backend.music_store.dto.user.request.FindByEmailRequestDto;
import dh.backend.music_store.dto.user.response.FindByEmailResponseDto;
import dh.backend.music_store.entity.Product;
import dh.backend.music_store.entity.Reservation;
import dh.backend.music_store.entity.ReservationStatus;
import dh.backend.music_store.entity.Users;
import dh.backend.music_store.exception.BadRequestException;
import dh.backend.music_store.repository.IReservationRepository;
import dh.backend.music_store.service.IProductService;
import dh.backend.music_store.service.IReservationService;
import dh.backend.music_store.dto.reservation.response.ReservationByProductResponseDto;
import dh.backend.music_store.service.IUserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService implements IReservationService {

    private final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    final IReservationRepository reservationRepository;
    private IProductService productService;
    private IUserService userService;

    @Autowired
    private ModelMapper modelMapper;

    public ReservationService(IReservationRepository reservationRepository, IProductService productService, IUserService userService) {
        this.reservationRepository = reservationRepository;
        this.productService = productService;
        this.userService = userService;
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

    @Override
    public ReservationByProductResponseDto save(SaveReservationRequestDto reservationRequest) {
        Reservation reservationToSave = new Reservation();

        //validamos que la fecha de fin sea posterior a la de inicio
        LocalDate startDate = LocalDate.parse(reservationRequest.getStartDate());
        LocalDate endDate = LocalDate.parse(reservationRequest.getEndDate());
        logger.info("Fechas parseadas correctamente");
        if(startDate.isAfter(endDate)){
            throw new BadRequestException("La fecha de fin debe ser posterior a la de inicio");
        }
        //validamos que las fechas seleccionadas sean posterior al dia actual
        if (startDate.isBefore(LocalDate.now())) {
            throw new BadRequestException("No se pueden realizar reservas con fechas pasadas");
        }

        logger.info("Validando existencia de producto y usuario");
        //validamos que el producto exista y el usuario exista

        DetailProductResponseDto productResponse = productService.findDetailsById(reservationRequest.getProductId());
        FindByEmailRequestDto userRequest = new FindByEmailRequestDto(reservationRequest.getUserEmail());
        FindByEmailResponseDto userResponse = userService.finByEmail(userRequest).getData(); //ojo

        logger.info("Accion de mapeo...");
        Users userDb = modelMapper.map(userResponse, Users.class); //mapeo
        Product productDb = modelMapper.map(productResponse, Product.class);

        //validamos la no existencia de reservacion del producto en fechas implicadas.
        logger.info("Buscando reservas registradas del producto " + productDb.getId());
        List<ReservationByProductProjection> reservations= reservationRepository.findReservationsByProduct(productDb.getId());
        boolean productIsAvailable = productService.productIsAvailable(startDate, endDate, reservations);

        if(productIsAvailable){
            //seteado de datos
            reservationToSave.setUser(userDb);
            reservationToSave.setProduct(productDb);
            reservationToSave.setStatus(ReservationStatus.APPROVED);
            reservationToSave.setStartDate(startDate);
            reservationToSave.setEndDate(endDate);
            reservationToSave.setCreationDate(LocalDate.now());
            //guardado
            Reservation reservationSaved =  reservationRepository.save(reservationToSave);
            return modelMapper.map(reservationSaved, ReservationByProductResponseDto.class);
        }else{
            throw new BadRequestException("El producto no esta disponible para la fecha seleccionada");
        }
    }

}
