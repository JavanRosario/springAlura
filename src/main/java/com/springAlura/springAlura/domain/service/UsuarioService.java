package com.springAlura.springAlura.domain.service;

import com.springAlura.springAlura.api.dto2.UsuarioAtualizacaoRequestDto;
import com.springAlura.springAlura.api.dto2.UsuarioAtualizarSenhaDto;
import com.springAlura.springAlura.api.dto2.UsuarioCriacaoRequestDto;
import com.springAlura.springAlura.api.dto2.UsuarioResponseDto;
import com.springAlura.springAlura.domain.exception.SenhaNaoConcideComAtualException;
import com.springAlura.springAlura.domain.exception.SerieNaoEncontradaException;
import com.springAlura.springAlura.domain.model.Usuario;
import com.springAlura.springAlura.domain.repositories.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Usuario buscaOuFalha(Long serieId) {
        return repository.findById(serieId).orElseThrow(() -> new SerieNaoEncontradaException(serieId));
    }

    public UsuarioResponseDto toDto(Usuario usuario) {
        UsuarioResponseDto dto = new UsuarioResponseDto();

        // forma manual
//		SerieResponseDto dto = new SerieResponseDto(serieAtual.getId(), serieAtual.getTitle(),
//				serieAtual.getTotalSeasons(), serieAtual.getImdbRating(), serieAtual.getActors(),
//				serieAtual.getPoster(), serieAtual.getPlot());

        BeanUtils.copyProperties(usuario, dto);
        return dto;
    }

    public List<UsuarioResponseDto> toDtoList(List<Usuario> usuario) {

        List<UsuarioResponseDto> dtos = usuario.stream().map(a -> {

            UsuarioResponseDto usuarioDto = new UsuarioResponseDto();

            BeanUtils.copyProperties(a, usuarioDto);
            return usuarioDto;

        }).toList();

        return dtos;
    }

//    public Usuario toDomain(UsuarioCriacaoRequestDto dto) {
//        Usuario usuario = new Usuario();
//
//        // forma manual
////		SerieResponseDto dto = new SerieResponseDto(serieAtual.getId(), serieAtual.getTitle(),
////				serieAtual.getTotalSeasons(), serieAtual.getImdbRating(), serieAtual.getActors(),
////				serieAtual.getPoster(), serieAtual.getPlot());
//
//        BeanUtils.copyProperties(dto, usuario);
//        return usuario;
//    }

//    public Usuario toDomainUsuarioAtualizacaoSemSenha(UsuarioAtualizacaoRequestDto dto) {
//        Usuario usuario = new Usuario();
//
//        // forma manual

    /// /		SerieResponseDto dto = new SerieResponseDto(serieAtual.getId(), serieAtual.getTitle(),
    /// /				serieAtual.getTotalSeasons(), serieAtual.getImdbRating(), serieAtual.getActors(),
    /// /				serieAtual.getPoster(), serieAtual.getPlot());
//
//        BeanUtils.copyProperties(dto, usuario);
//        return usuario;
//    }
    public List<Usuario> listar() {
        return repository.findAll();
    }

    public Usuario toDomainUsuarioCriacao(UsuarioCriacaoRequestDto dto) {
        Usuario usuario = new Usuario();

        // forma manual
//		SerieResponseDto dto = new SerieResponseDto(serieAtual.getId(), serieAtual.getTitle(),
//				serieAtual.getTotalSeasons(), serieAtual.getImdbRating(), serieAtual.getActors(),
//				serieAtual.getPoster(), serieAtual.getPlot());

        BeanUtils.copyProperties(dto, usuario, "password");
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        return usuario;
    }

    public Usuario salvarUsuarioCriacao(UsuarioCriacaoRequestDto dto) {
        Usuario usuario = toDomainUsuarioCriacao(dto);
        return repository.save(usuario);
    }

    public Usuario salvar(Usuario usuario) {
        return repository.save(usuario);
    }

    public Usuario atualizar(Long usuarioId, UsuarioAtualizacaoRequestDto dto) {
        Usuario usuario = buscaOuFalha(usuarioId);
        BeanUtils.copyProperties(dto, usuario, "password");
        return usuario = salvar(usuario);
    }

    public Usuario atualizarSenha(Long usuarioId, UsuarioAtualizarSenhaDto dto) {
        Usuario usuario = buscaOuFalha(usuarioId);

        if (!dto.getPassword().equalsIgnoreCase(usuario.getPassword())) {
            throw new SenhaNaoConcideComAtualException(usuarioId);
        }

        usuario.setPassword(dto.getSenhaNova());

        return usuario = salvar(usuario);
    }
}
