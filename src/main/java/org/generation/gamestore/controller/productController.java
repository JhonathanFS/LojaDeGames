package org.generation.gamestore.controller;

import java.util.List;

import org.generation.gamestore.model.product;
import org.generation.gamestore.repository.productRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class productController {
	
	@Autowired
	public productRepository repository;
	
	@GetMapping
	public ResponseEntity <List<product>> GetAll(){
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<product> GetById(@PathVariable Long id){
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<product>> GetByNome (@PathVariable String nome){
		return ResponseEntity.ok(repository.findAllByNomeContainingIgnoreCase(nome));	
	}
	
	@PostMapping
	public ResponseEntity<product> post (@RequestBody product product){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(repository.save(product));
	}
	
	@PutMapping
	public ResponseEntity<product> put (@RequestBody product product){
		return ResponseEntity.status(HttpStatus.OK)
				.body(repository.save(product));
	}
	 
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id){
		repository.deleteById(id);
	
	}
}
