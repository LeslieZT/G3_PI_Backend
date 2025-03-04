package dh.backend.music_store.controller;

import dh.backend.music_store.dto.Generic.PaginationResponseDto;
import dh.backend.music_store.dto.Generic.ResponseDto;
import dh.backend.music_store.dto.user.request.ChangeRoleUserRequestDto;
import dh.backend.music_store.dto.user.request.CreateUserDto;
import dh.backend.music_store.dto.user.request.FindAllUserRequestDto;
import dh.backend.music_store.dto.user.request.RegisterUserRequestDto;
import dh.backend.music_store.dto.user.response.ChangeRoleResponseDto;
import dh.backend.music_store.dto.user.response.FindAllUserResponseDto;

import dh.backend.music_store.dto.user.response.RegisterUserResponseDto;
import dh.backend.music_store.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/find-all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaginationResponseDto<FindAllUserResponseDto>>  findAll(@Valid @RequestBody(required = false) FindAllUserRequestDto request){
        PaginationResponseDto<FindAllUserResponseDto> response = userService.findAll(request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/users/change-role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDto<ChangeRoleResponseDto>> changeRole(@Valid @RequestBody(required = false) ChangeRoleUserRequestDto request){
        ResponseDto<ChangeRoleResponseDto> response = userService.changeRole(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/users/register")
    public ResponseEntity<RegisterUserResponseDto> registerUser(@Valid @RequestBody RegisterUserRequestDto request) {
        RegisterUserResponseDto response = userService.registerUser(request);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/user/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext(); // Limpia el contexto de seguridad
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Invalida la sesión
        }
        return ResponseEntity.ok("Sesión cerrada correctamente");
    }


}
