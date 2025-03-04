package dh.backend.music_store.service.impl;

import dh.backend.music_store.entity.Users;
import dh.backend.music_store.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario "+email +" no existe"));

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_".concat(user.getRole().getName().name()));

        return new User(user.getEmail(),user.getPassword(),
                true, true,
                true, true, List.of(authority)); // Usar collections si es nn roles
    }
}
