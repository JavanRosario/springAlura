//package com.springAlura.springAlura.domain.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.springAlura.springAlura.domain.repositories.UsuarioRepository;
//
//@Service
//public class AuthencicacaoService implements UserDetailsService {
//
//	@Autowired
//	private UsuarioRepository repo;
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		// TODO Auto-generated method stub
//		return repo.findByLogin(username)
//				.orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado..." + username));
//	}
//}
