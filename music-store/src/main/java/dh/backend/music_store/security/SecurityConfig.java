package dh.backend.music_store.security;


import dh.backend.music_store.security.filter.JwtAuthenticationFilter;
import dh.backend.music_store.security.filter.JwtAuthorizationFilter;
import dh.backend.music_store.security.jwt.JwtUtils;
import dh.backend.music_store.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    JwtAuthorizationFilter jwtAuthorizationFilter;


        //CONFIGURACIÓN DE ACCESO A LOS ENDPOINTS
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");

        return httpSecurity.csrf(config -> config.disable()) // SOlo activar para uso de formularios
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/users/register","/login").permitAll();
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::migrateSession) // Protección contra fijación de sesión //migrateSession -> crea nueva session
                        .invalidSessionUrl("/login") // Manejo de sesión inválida
                        .maximumSessions(1)
                        .expiredUrl("/login") // Redirigir si la sesión expira
                        .sessionRegistry(sessionRegistry()))
                .logout(logout -> logout
                        .logoutUrl("/user/logout") // URL para cerrar sesión
                        .invalidateHttpSession(true) // Invalidar la sesión
                        .deleteCookies("JSESSIONID") // Eliminar cookies de sesión
                        .permitAll()
                )
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    public AuthenticationSuccessHandler successHandler(){
        return ((request, response, authentication) -> {
            response.sendRedirect("/test/session");
        });
    }

    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
