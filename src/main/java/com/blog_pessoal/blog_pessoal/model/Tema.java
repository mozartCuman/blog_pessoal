package com.blog_pessoal.blog_pessoal.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_temas")
public class Tema {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = " A DESCRIÇÃO é obrigatória")
	private String descricao;
	
	
	//OneToMany Indica que a classe terá a relação 1:N e terá uma Collection List tendo Objetos da Cl Postagem
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tema", cascade = CascadeType.REMOVE) //fetch define a estratégia de busca de carregamento
	@JsonIgnoreProperties("tema") // mappedBy dá o nome da classe proprietária que foi criado na classe filha, cascade indica relacionamentos de entidades, nesse caso, se excluida a tema, postagem também será.
	private List<Postagem> postagem;
	//O JsonIgnoreProperties aqui está evitando um looping por recursividade.
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Postagem> getPostagem() {
		return postagem;
	}

	public void setPostagem(List<Postagem> postagem) {
		this.postagem = postagem;
	}
	

}
