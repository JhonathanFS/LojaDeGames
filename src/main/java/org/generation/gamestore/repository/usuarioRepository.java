package org.generation.gamestore.repository;

import java.util.Optional;

import org.generation.gamestore.model.usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface usuarioRepository extends JpaRepository<usuario, Long> {

	public Optional<usuario> findByUsuario(String usuario); 

}
