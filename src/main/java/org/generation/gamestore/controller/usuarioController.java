package org.generation.gamestore.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.generation.gamestore.model.usuario;
import org.generation.gamestore.model.usuarioLogin;
import org.generation.gamestore.repository.usuarioRepository;
import org.generation.gamestore.service.UsuarioService;
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
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins="*", allowedHeaders="*")
public class usuarioController {
	
	@Autowired
	private UsuarioService service;
	
	@Autowired
	private usuarioRepository repository;
	
	
	@GetMapping("/all")	
		public ResponseEntity <List<usuario>> getAll(){
		return ResponseEntity.ok(repository.findAll());
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<usuario> CadastrarUsuario(@Valid @RequestBody usuario usuario){
		return service.cadastrarUsuario(usuario)
				.map(resp -> ResponseEntity.status(HttpStatus.CREATED).body(resp))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
	
	@PostMapping("/logar")
	public ResponseEntity<usuarioLogin> AutenticarUsuario(@Valid @RequestBody Optional<usuarioLogin> user) {
		return service.autenticarUsuario(user)
			.map(resp -> ResponseEntity.ok(resp))
			.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}			
	
	@PutMapping("/atualizar")
	public ResponseEntity<usuario> putUsuario(@Valid @RequestBody usuario usuario){		
		return service.atualizarUsuario(usuario)
			.map(resp -> ResponseEntity.status(HttpStatus.OK).body(resp))
			.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
	
	@DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
	
}