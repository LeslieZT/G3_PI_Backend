package dh.backend.music_store.service;

import dh.backend.music_store.dto.Generic.ResponseDto;
import dh.backend.music_store.dto.auth.request.LoginRequestDto;
import dh.backend.music_store.dto.auth.request.RegisterRequestDto;
import dh.backend.music_store.dto.auth.response.LoginResponseDto;
import dh.backend.music_store.dto.auth.response.RegisterResponseDto;


public interface IAuthenticationService {
    ResponseDto<RegisterResponseDto> register(RegisterRequestDto request);
    ResponseDto<LoginResponseDto> login(LoginRequestDto request);

}
