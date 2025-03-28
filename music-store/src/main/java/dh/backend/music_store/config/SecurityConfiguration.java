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
                            auth.requestMatchers(HttpMethod.GET, "/reservations/products/**").permitAll();


                            // endpoints que requieren autenticacion basica (tener al menos el rol de user)
                            auth.requestMatchers(HttpMethod.POST, "/users/find-by-email").authenticated();

                            // endponints que necesitan algun tipo de rol especifico
                            auth.requestMatchers("/backoffice/**").hasAnyAuthority("ADMIN");
                            auth.requestMatchers(HttpMethod.POST, "/api/upload/image").hasAuthority("ADMIN");
                            auth.requestMatchers(HttpMethod.POST, "/products/save").hasAuthority("ADMIN");

                            auth.anyRequest().authenticated();

                        })
                .csrf(config -> config.disable())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider)
                .build();
    }


}
