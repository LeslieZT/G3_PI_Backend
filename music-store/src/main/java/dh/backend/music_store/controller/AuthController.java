package dh.backend.music_store.controller;


import dh.backend.music_store.dto.Generic.ResponseDto;
import dh.backend.music_store.dto.auth.request.LoginRequestDto;
import dh.backend.music_store.dto.auth.request.RegisterRequestDto;
import dh.backend.music_store.dto.auth.response.LoginResponseDto;
import dh.backend.music_store.dto.auth.response.RegisterResponseDto;
import dh.backend.music_store.service.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthenticationService authenticationService;

    @PostMapping("/register")
    ResponseEntity<ResponseDto<RegisterResponseDto>> register(@RequestBody RegisterRequestDto request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    ResponseEntity<ResponseDto<LoginResponseDto>> login(@RequestBody LoginRequestDto request){
        return ResponseEntity.ok(authenticationService.login(request));
    }

}
