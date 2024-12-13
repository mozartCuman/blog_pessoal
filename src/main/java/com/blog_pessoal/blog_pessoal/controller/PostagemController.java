package com.blog_pessoal.blog_pessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.blog_pessoal.blog_pessoal.model.Postagem;
import com.blog_pessoal.blog_pessoal.repository.PostagemRepository;
import com.blog_pessoal.blog_pessoal.repository.TemaRepository;

import jakarta.validation.Valid;

@RestController // Define que a classe receberá requisições de URL (endpoint), verbo(Define o
				// metodo Http acionado e corpo de requisição (requestbody)
@RequestMapping("/postagens") // Mapea as solicitações dos metodos, define um url
								// (http://localhost:8080/postagens).
@CrossOrigin(origins = "*", allowedHeaders = "*") // Indica a aceitação de requisições fora do domínio
public class PostagemController {

	@Autowired // Injeção de dependencia, define quais Classes serão instanciadas e em quais
				// lugares serão Injetadas quando houver necessidade.
	private PostagemRepository postagemRepository; // Apontando a delegação do autowired

	@Autowired
	private TemaRepository temaRepository; //Agora dando acesso a os metodos das classes TEMA
	
	@GetMapping // Mapea todas as requisições Http GET
	public ResponseEntity<List<Postagem>> getAll() { // Retorna um objt Cl ResponseEntity (OK -> 200) e retorna o objt Cl List (Collection)
		return ResponseEntity.ok(postagemRepository.findAll()); // findAll() == método padrão JPARe mesmo vazio retorna 200
	}

	@GetMapping("/{id}")
	public ResponseEntity<Postagem> getById(@PathVariable Long id) { // PathVariable insere o valor enviado no endereço do endpoint, na variavel de caminho {id}
		return postagemRepository.findById(id) // findById é padrão do repository
				.map(resposta -> ResponseEntity.ok(resposta)) // map tem como principal função evitar o NullPointException
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // se o objt não for encontrado retorna 404
	}

	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}

	@PostMapping
	public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem) {// Valid valida o o Objt de acordo com a model, RequestBody recebe oJson e poe em postagem
		if (temaRepository.existsById(postagem.getTema().getId())) //existsById da interface temaRepository, checamos a existencia pelo id do tema	
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(postagemRepository.save(postagem));
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não existe!!!", null);
	}

	@PutMapping
	public ResponseEntity<Postagem> put(@Valid @RequestBody Postagem postagem) {
		if (postagemRepository.existsById(postagem.getId())) {
			if (temaRepository.existsById(postagem.getTema().getId()))
				return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem));

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não existe!", null);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT) //Indica que tera um status Http caso a requisição seja bem sucedida.
	@DeleteMapping("/delete/id")  //Mapeia todas as Requisições HTTP DELETE.
	public void delete(@PathVariable Long id) {  //é void pois ao deletar um objeto ele deixa de existir, logo não tem nada para retornar
		Optional<Postagem> postagem = postagemRepository.findById(id); //Cria um Objt Optional para receber o findById() e evitar erro NullPointerExeption.
		
		if (postagem.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);// Se a postagem for nula retorna 404
		postagemRepository.deleteById(id); // deleteById() Padrão do JPARe executa retornando 204
	}
}
