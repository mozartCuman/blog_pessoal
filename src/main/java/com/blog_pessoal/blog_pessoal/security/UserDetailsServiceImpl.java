package com.blog_pessoal.blog_pessoal.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.blog_pessoal.blog_pessoal.model.Usuario;
import com.blog_pessoal.blog_pessoal.repository.UsuarioRepository;

@Service //Indica que é uma classe de serviço, responsável por implementar uma regra de negocio e tratativas de dados de uma parte do ou recurso do sistema
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{ // recebe o nome de usuario atraves da tela de login 
		Optional<Usuario> usuario = usuarioRepository.findByUsuarioContainingIgnoreCase(userName); //recebe o usuario atraves da query method implementada no repository para averiguar o banco de dados
		if (usuario.isPresent())
			return new UserDetailsImpl(usuario.get());
		else
			throw new ResponseStatusException(HttpStatus.FORBIDDEN); //FORBIDDEN = 403(Acesso Proibido)
	}

}
