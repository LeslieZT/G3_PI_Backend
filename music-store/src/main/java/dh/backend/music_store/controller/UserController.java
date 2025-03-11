package dh.backend.music_store.controller;

import dh.backend.music_store.dto.Generic.PaginationResponseDto;
import dh.backend.music_store.dto.Generic.ResponseDto;
import dh.backend.music_store.dto.user.request.ChangeRoleUserRequestDto;
import dh.backend.music_store.dto.user.request.FindAllUserRequestDto;
import dh.backend.music_store.dto.user.request.FindByEmailRequestDto;
import dh.backend.music_store.dto.user.request.RegisterUserRequestDto;
import dh.backend.music_store.dto.user.response.ChangeRoleResponseDto;
import dh.backend.music_store.dto.user.response.FindAllUserResponseDto;
import dh.backend.music_store.dto.user.response.FindByEmailResponseDto;
import dh.backend.music_store.dto.user.response.RegisterUserResponseDto;
import dh.backend.music_store.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    private IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/backoffice/users/find-all")
    public ResponseEntity<PaginationResponseDto<FindAllUserResponseDto>>  findAll(@Valid @RequestBody(required = false) FindAllUserRequestDto request){
        PaginationResponseDto<FindAllUserResponseDto> response = userService.findAll(request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/backoffice/users/change-role")
    public ResponseEntity<ResponseDto<ChangeRoleResponseDto>> changeRole(@Valid @RequestBody(required = false) ChangeRoleUserRequestDto request){
        ResponseDto<ChangeRoleResponseDto> response = userService.changeRole(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/backoffice/users/register")
    public ResponseEntity<ResponseDto<RegisterUserResponseDto>> registerUser(@Valid @RequestBody RegisterUserRequestDto request) {
        ResponseDto<RegisterUserResponseDto>  response = userService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/users/find-by-email")
    public ResponseEntity<ResponseDto<FindByEmailResponseDto>> finByEmail(@Valid @RequestBody FindByEmailRequestDto findByEmailRequestDto){
        ResponseDto<FindByEmailResponseDto> responseDto = userService.finByEmail(findByEmailRequestDto);
        return ResponseEntity.ok(responseDto);
    }
}
