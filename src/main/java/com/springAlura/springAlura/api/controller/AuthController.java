package com.springAlura.springAlura.api.controller;

import com.springAlura.springAlura.api.dto2.LoginResponseDto;
import com.springAlura.springAlura.api.dto2.UsuarioCriacaoRequestDto;
import com.springAlura.springAlura.api.dto2.UsuarioLoginRequest;
import com.springAlura.springAlura.api.dto2.UsuarioResponseDto;
import com.springAlura.springAlura.configs.TokenConfig;
import com.springAlura.springAlura.domain.model.Usuario;
import com.springAlura.springAlura.domain.repositories.UsuarioRepository;
import com.springAlura.springAlura.domain.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final TokenConfig tokenConfig;
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UsuarioRepository usuarioRepository, UsuarioService usuarioService, AuthenticationManager authenticationManager
            , TokenConfig tokenConfig) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.tokenConfig = tokenConfig;
    }

    @PostMapping("/login")
    public LoginResponseDto login(@Valid @RequestBody UsuarioLoginRequest login) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);

        Usuario usuario = (Usuario) authentication.getPrincipal();
        String userToken = tokenConfig.generateToken(usuario);

        return new LoginResponseDto(userToken);
    }

    @PostMapping("/register")
    public UsuarioResponseDto registro(@RequestBody UsuarioCriacaoRequestDto request) {
        Usuario usuario = usuarioService.toDomainUsuarioCriacao(request);
        usuario = usuarioService.salvar(usuario);
        return usuarioService.toDto(usuario);
    }
}
