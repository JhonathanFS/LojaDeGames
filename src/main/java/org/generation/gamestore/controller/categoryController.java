package org.generation.gamestore.controller;

import java.util.List;

import org.generation.gamestore.model.category;
import org.generation.gamestore.repository.categoryRepository;
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
@RequestMapping("/categorys")
@CrossOrigin(origins="*", allowedHeaders="*")
public class categoryController {
	
	@Autowired
	public categoryRepository repository;
	
	@GetMapping
	public ResponseEntity <List<category>> GetAll(){
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<category> GetById(@PathVariable Long id){
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/tipo/{tipo}")
	public ResponseEntity<List<category>> GetByTipo (@PathVariable String tipo){
		return ResponseEntity.ok(repository.findAllByTipoContainingIgnoreCase(tipo));	
	}
	
	@PostMapping
	public ResponseEntity<category> post (@RequestBody category category){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(repository.save(category));
	}
	
	@PutMapping
	public ResponseEntity<category> put (@RequestBody category category){
		return ResponseEntity.status(HttpStatus.OK)
				.body(repository.save(category));
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id){
		repository.deleteById(id);
	}

}
