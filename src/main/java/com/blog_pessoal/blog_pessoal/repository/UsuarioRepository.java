package com.blog_pessoal.blog_pessoal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog_pessoal.blog_pessoal.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	public Optional<Usuario> findByUsuario(String usuario);

	public Optional<Usuario> findByUsuarioContainingIgnoreCase(String userName);
	
}
