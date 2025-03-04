package dh.backend.music_store.service;

import dh.backend.music_store.dto.Generic.PaginationResponseDto;
import dh.backend.music_store.dto.Generic.ResponseDto;
import dh.backend.music_store.dto.user.request.ChangeRoleUserRequestDto;
import dh.backend.music_store.dto.user.request.CreateUserDto;
import dh.backend.music_store.dto.user.request.FindAllUserRequestDto;
import dh.backend.music_store.dto.user.response.ChangeRoleResponseDto;
import dh.backend.music_store.dto.user.response.FindAllUserResponseDto;
import dh.backend.music_store.dto.user.response.RegisterUserDto;

import java.util.List;

public interface IUserService {
    PaginationResponseDto<FindAllUserResponseDto> findAll(FindAllUserRequestDto request);
    ResponseDto<ChangeRoleResponseDto> changeRole(ChangeRoleUserRequestDto request);

    RegisterUserDto saveUser(CreateUserDto createUserDto);

    String deleteUser(String id);
}
