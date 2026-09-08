    package com.springAlura.springAlura.configs.security;

    import com.springAlura.springAlura.domain.repositories.UsuarioRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.stereotype.Service;

    @Service
    public class AuthConfig implements UserDetailsService {

        @Autowired
        UsuarioRepository usuarioRepository;


        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return usuarioRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
        }
    }
