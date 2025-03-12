package dh.backend.music_store.service.impl;

import dh.backend.music_store.dto.Generic.PaginationResponseDto;
import dh.backend.music_store.dto.Generic.ResponseDto;
import dh.backend.music_store.dto.user.projection.FilteredUserProjection;
import dh.backend.music_store.dto.user.request.ChangeRoleUserRequestDto;
import dh.backend.music_store.dto.user.request.FindAllUserRequestDto;
import dh.backend.music_store.dto.user.request.FindByEmailRequestDto;
import dh.backend.music_store.dto.user.request.RegisterUserRequestDto;
import dh.backend.music_store.dto.user.response.*;
import dh.backend.music_store.entity.Role;
import dh.backend.music_store.entity.Users;
import dh.backend.music_store.exception.BadRequestException;
import dh.backend.music_store.exception.ResourceNotFoundException;
import dh.backend.music_store.repository.IRoleRepository;
import dh.backend.music_store.repository.IUserRepository;
import dh.backend.music_store.service.IUserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;


@Service
public class UserService implements IUserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final IUserRepository userRepository;

    private final IRoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    public UserService(IUserRepository userRepository, IRoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public PaginationResponseDto<FindAllUserResponseDto> findAll(FindAllUserRequestDto request) {
        if (request == null) {
            request = new FindAllUserRequestDto();
        }
        int offset = (request.getPage() - 1) * request.getLimit();

        logger.info("Request: {}", request);

        boolean hasRoles = request.getRoles() != null && !request.getRoles().isEmpty();

        logger.info("HasRoles: {}", hasRoles);

        List<FilteredUserProjection> usersDB = userRepository.filterUsers(request.getSearch(), request.getRoles(), hasRoles,  request.getLimit(), offset);

        Integer totalDB = userRepository.countFilterUsers(request.getSearch(), request.getRoles(), hasRoles);

        List<FindAllUserResponseDto> data = usersDB.stream()
                .map(projection -> new FindAllUserResponseDto(
                       projection.getId(),
                        projection.getFirstName(),
                        projection.getLastName(),
                        projection.getEmail(),
                        new RoleResponseDto(projection.getRoleId(), projection.getRoleName())
                )).toList();;

        PaginationResponseDto<FindAllUserResponseDto> response = new PaginationResponseDto<FindAllUserResponseDto>();
        response.setData(data);
        response.setPage(request.getPage());
        response.setSize(request.getLimit());
        response.setTotal(totalDB);
        return response;
    }

    @Override
    public ResponseDto<ChangeRoleResponseDto> changeRole(ChangeRoleUserRequestDto request) {
        Optional<Users> userDB = userRepository.findById(request.getUserId());
        if (userDB.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }

        Optional<Role> roleDB = roleRepository.findById(request.getRoleId());
        if (roleDB.isEmpty()) {
            throw new ResourceNotFoundException("Role not found");
        }
        userDB.get().setRole(roleDB.get());
        userRepository.save(userDB.get());
        ResponseDto<ChangeRoleResponseDto> response = new ResponseDto<>();
        response.setData(new ChangeRoleResponseDto("Role changed successfully"));
        return response;

    }

    @Override
    public ResponseDto<RegisterUserResponseDto>  registerUser(RegisterUserRequestDto request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("The email is already registered");
        }
        Users user =  modelMapper.map(request, Users.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(roleRepository.findById(2).orElseThrow(() -> new RuntimeException("Role not found")));
        userRepository.save(user);
        ResponseDto<RegisterUserResponseDto> response = new ResponseDto<>();
        response.setData(new RegisterUserResponseDto("The user was created successfully "));
        return response;
    }

    @Override
    public ResponseDto<FindByEmailResponseDto> finByEmail(FindByEmailRequestDto findByEmailRequestDto){
        Users users = userRepository.findByEmail(findByEmailRequestDto.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        FindByEmailResponseDto responseDto = modelMapper.map(users, FindByEmailResponseDto.class);
        responseDto.setRole(String.valueOf(users.getRole().getName()));
        ResponseDto<FindByEmailResponseDto> response = new ResponseDto<>();
        response.setData(responseDto);
        return response;
    }

}
