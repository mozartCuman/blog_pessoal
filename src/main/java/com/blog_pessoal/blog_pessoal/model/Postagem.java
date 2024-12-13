package com.blog_pessoal.blog_pessoal.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

//  O pacote Jakarta está disponível apenas a partir da versão 3.0.0 do Spring.

@Entity // Entity indica que esta Classe define uma entidade, resumindo gera uma tabela
		// no BDD.
@Table(name = "tb_postagem") // Table está apresentando nome da Tabela no BDD (caso o nome não declarado o
								// default é o nome da classe).
public class Postagem {

	@Id // Indicação de PrimaryKey
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Indicação de AUTO-INCREMENT no SQL.
	private Long id; // Tipo Java -> Long == BIGINT -> Tipo SQL.

	@NotBlank(message = " O TÍTULO é OBRIGATÓRIO !!! ") // Não pode ser nulo.
	@Size(min = 3, max = 100, message = " O atributo deve conter no MÍNIMO 3 e no MÁXIMO 100 caracteres!!! ") // Tamanho
	private String titulo; // Tipo Java -> String == VARCHAR -> Tipo SQL.

	@NotBlank(message = " O TEXTO é OBRIGATÓRIO !!! ")
	@Size(min = 3, max = 1000, message = " O atributo deve conter no MÍNIMO 3 e no MÁXIMO 1000 caracteres!!! ")
	private String texto;

	@UpdateTimestamp // Spring se encarregará de data e hora do Sistema e inserirá no Atributo data
						// toda vez que for criado ou atualizado
	private LocalDateTime data;// Tipo Java -> LocalDateTime == DATETIME -> Tipo SQL.

	@ManyToOne //Indica que a Cl Postagem esta no lado N:1 e terá um Objt Tema com sua chaver estrangeira relacionada.
	@JsonIgnoreProperties("postagem") //Indica que uma parte do Json será ignorado, assim com as relação das classes sendo bidirecional
	private Tema tema; // O objeto tema será criado com um "Sub Objeto" do objeto postagem.
	
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Usuario usuario;

	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

}
