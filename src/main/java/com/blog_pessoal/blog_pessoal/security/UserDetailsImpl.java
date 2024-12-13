package com.blog_pessoal.blog_pessoal.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.blog_pessoal.blog_pessoal.model.Usuario;

public class UserDetailsImpl implements UserDetails {
	
	private static final Long serialVersionUID = 1L; //Identificador de versao de classe usada para serializar e desserializarum OBJT
	
	private String userName;
	private String password;
	private List<GrantedAuthority> authorities;
	
	public UserDetailsImpl(Usuario user) {
		this.userName = user.getUsuario();
		this.password = user.getSenha();
	}
	
	public UserDetailsImpl() {}//metodo construtor vazio gerando Polimorfismo de Sobrecarga, sera usado para gerar objetos com atributos nao preenchidos
	
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;// metodo responsavel por retornar Direitos de acesso do usuario
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {// indica se a credencial (token precisa ser trocado)
		return true;
	}
	
	@Override
	public boolean isEnabled() {//Indica se um usuario esta ou não habilitado
		return true;
	}
	
	

}
