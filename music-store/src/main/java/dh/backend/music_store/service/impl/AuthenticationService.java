package dh.backend.music_store.service.impl;
import dh.backend.music_store.config.JwtService;
import dh.backend.music_store.dto.Generic.ResponseDto;
import dh.backend.music_store.dto.auth.request.LoginRequestDto;
import dh.backend.music_store.dto.auth.request.RegisterRequestDto;
import dh.backend.music_store.dto.auth.response.LoginResponseDto;
import dh.backend.music_store.dto.auth.response.RegisterResponseDto;
import dh.backend.music_store.entity.Users;
import dh.backend.music_store.exception.BadRequestException;
import dh.backend.music_store.repository.IRoleRepository;
import dh.backend.music_store.repository.IUserRepository;
import dh.backend.music_store.service.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements IAuthenticationService {

    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final IUserRepository userRepository;

    private final IRoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    private ModelMapper modelMapper;

    public AuthenticationService(IUserRepository userRepository, IRoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public ResponseDto<RegisterResponseDto> register(RegisterRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("The email is already registered");
        }
        Users user =  modelMapper.map(request, Users.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(roleRepository.findById(2).orElseThrow(() -> new RuntimeException("Role not found")));
        userRepository.save(user);
        ResponseDto<RegisterResponseDto> response = new ResponseDto<>();
        response.setData(new RegisterResponseDto("The user was created successfully "));
        return response;
    }

    @Override
    public ResponseDto<LoginResponseDto> login(LoginRequestDto request) {
        Optional<Users> usuario = userRepository.findByEmail(request.getEmail());
        if (usuario.isEmpty()) {
            throw new BadRequestException("User not found");
        }
        logger.info("User found");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new BadRequestException("Invalid credentials");
        }
        logger.info("Generate token");
        String token = jwtService.generateToken(usuario.get());
        LoginResponseDto response = new LoginResponseDto();
        response.setToken(token);
        response.setRole(usuario.get().getRole().getName().name());
        ResponseDto<LoginResponseDto> responseDto = new ResponseDto<>();
        responseDto.setData(response);
        return responseDto;
    }
}
