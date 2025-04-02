package dh.backend.music_store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(AuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(
                        auth ->{
                            // endpoints que no necesitan autenticacion
                            auth.requestMatchers("/auth/**").permitAll();
                            auth.requestMatchers(HttpMethod.GET, "/categories/**").permitAll();
                            auth.requestMatchers(HttpMethod.GET, "/brands/**").permitAll();
                            auth.requestMatchers(HttpMethod.GET, "/products/**").permitAll();
                            auth.requestMatchers(HttpMethod.POST, "/products/search").permitAll();
                            auth.requestMatchers(HttpMethod.POST, "/products/find-all").permitAll();
                            auth.requestMatchers(HttpMethod.POST, "/products/search").permitAll();
                            auth.requestMatchers(HttpMethod.GET, "/reservations/products/**").permitAll();

                            // endpoints que requieren autenticacion basica (tener al menos el rol de user)
                            auth.requestMatchers(HttpMethod.POST, "/users/find-by-email").authenticated();
                            auth.requestMatchers(HttpMethod.POST, "/reservations/save").authenticated();

                            // endponints que necesitan algun tipo de rol especifico
                            auth.requestMatchers("/backoffice/**").hasAnyAuthority("ADMIN");
                            auth.requestMatchers(HttpMethod.POST, "/api/upload/image").hasAuthority("ADMIN");
                            auth.requestMatchers(HttpMethod.POST, "/products/save").hasAuthority("ADMIN");
                            auth.requestMatchers(HttpMethod.PUT, "/products/update").hasAuthority("ADMIN");
                            auth.requestMatchers(HttpMethod.DELETE, "/products/delete/**").hasAuthority("ADMIN");
                            auth.anyRequest().authenticated();

                        })
                .csrf(config -> config.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*")); // Permite cualquier origen
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")); // Métodos permitidos
        configuration.setAllowedHeaders(List.of("*")); // Permite cualquier header
        configuration.setAllowCredentials(false); // Permitir credenciales
        configuration.setExposedHeaders(List.of("Authorization", "Content-Type")); // Permite estos headers en la respuesta

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
