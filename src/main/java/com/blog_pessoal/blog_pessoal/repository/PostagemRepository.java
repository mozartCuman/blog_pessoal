package com.blog_pessoal.blog_pessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.blog_pessoal.blog_pessoal.model.Postagem;

//Nas versões anteriores a 3 do Spring, utiliza-se acima da assinatura da Interface Repository a anotação @Repository.
public interface PostagemRepository extends JpaRepository<Postagem, Long>{
	
	public List <Postagem> findAllByTituloContainingIgnoreCase(@Param("titulo") String titulo); //SQL equivalente SELECT * FROM tb_postagens WHERE titulo LIKE "%titulo%";


	
	
}


//Lembrando: Interface = Classe Abstrata (modelo) com apenas métodos abstratos e não pode ser instanciada.

// Classe postagem está sendo mapeada no BDD e o LONG representa a PRIMARY KEY

// O Repositório JPA é utilizado principalmente para gerenciar os dados em um APP Spring Boot. 