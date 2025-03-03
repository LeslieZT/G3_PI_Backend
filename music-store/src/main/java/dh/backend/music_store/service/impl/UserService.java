package dh.backend.music_store.service.impl;

import dh.backend.music_store.dto.Generic.PaginationResponseDto;
import dh.backend.music_store.dto.Generic.ResponseDto;
import dh.backend.music_store.dto.user.projection.FilteredUserProjection;
import dh.backend.music_store.dto.user.request.ChangeRoleUserRequestDto;
import dh.backend.music_store.dto.user.request.CreateUserDto;
import dh.backend.music_store.dto.user.request.FindAllUserRequestDto;
import dh.backend.music_store.dto.user.response.ChangeRoleResponseDto;
import dh.backend.music_store.dto.user.response.FindAllUserResponseDto;
import dh.backend.music_store.dto.user.response.RegisterUserDto;
import dh.backend.music_store.dto.user.response.RoleResponseDto;
import dh.backend.music_store.entity.Role;
import dh.backend.music_store.entity.User;
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

    private final ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(IUserRepository userRepository, IRoleRepository roleRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
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
        Optional<User> userDB = userRepository.findById(request.getUserId());
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

    //Encriptamos password
    private String encryptPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public RegisterUserDto saveUser(CreateUserDto createUserDto) {
        try {
            createUserDto.setPassword(encryptPassword(createUserDto.getPassword()));
            User user = modelMapper.map(createUserDto, User.class);

            // Buscar el rol con ID = 2 y asignarlo por default
            Role role = roleRepository.findById(2).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
            user.setRole(role);

            user = userRepository.save(user); // La entidad ya tiene el ID generado

            RegisterUserDto userDto = new RegisterUserDto(
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPassword(),
                    "Registro Exitoso"
            );

            return userDto;
        } catch (Exception e) {
            return new RegisterUserDto(null, null, null, null, "Error al Registrarse");
        }
    }
}
