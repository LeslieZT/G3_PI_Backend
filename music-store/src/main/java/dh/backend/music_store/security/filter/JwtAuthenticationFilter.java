package dh.backend.music_store.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import dh.backend.music_store.entity.Users;
import dh.backend.music_store.repository.IUserRepository;
import dh.backend.music_store.security.jwt.JwtUtils;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JwtUtils jwtUtils;

    public JwtAuthenticationFilter (JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        Users users = null;
        String userEmail;
        String password ="";
        try{
            users = new ObjectMapper().readValue(request.getInputStream(), Users.class);
            userEmail = users.getEmail();
            password = users.getPassword();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userEmail, password);

        return getAuthenticationManager().authenticate(authenticationToken);
    }


    //LOGIN EXITOSO
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        String token = jwtUtils.tokenGenerator(user.getUsername());

        // Obtener el contexto de Spring y recuperar el UserRepository
        ApplicationContext context = WebApplicationContextUtils
                .getRequiredWebApplicationContext(request.getServletContext());
        IUserRepository userRepository = context.getBean(IUserRepository.class);

        // Buscar usuario en la BD
        Users usersEntity = userRepository.findByEmail(user.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        response.addHeader("Authorization", token); //poner en body
        Map<String, Object> httpResponse = new HashMap<>();
        httpResponse.put("Message"," Autenticación Correcta");
        httpResponse.put("Username", user.getUsername());
        httpResponse.put("token", token);
        httpResponse.put("role",usersEntity.getRole().getName());

        response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().flush();
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
