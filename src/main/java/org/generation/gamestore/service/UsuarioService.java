package org.generation.gamestore.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.generation.gamestore.model.usuario;
import org.generation.gamestore.model.usuarioLogin;
import org.generation.gamestore.repository.usuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

public class UsuarioService {

	@Autowired
	private usuarioRepository repository;
	
	
	private String criptografarSenha(String senha) { 
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();  
		return encoder.encode(senha); 
	}

	public Optional<usuario> cadastrarUsuario(usuario usuario){  

		if(repository.findByUsuario(usuario.getUsuario()).isPresent()) { 
			return Optional.empty(); 		}
		
		usuario.setSenha(criptografarSenha(usuario.getSenha()));
		return Optional.of(repository.save(usuario)); 
	}
	
	private boolean compararSenhas(String senhaDigitada, String senhaDoBanco) { 
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		return encoder.matches(senhaDigitada, senhaDoBanco); 
	}
	
	private String geradorBasicToken(String usuario, String senha) { 

		String token = usuario + ":" + senha; 
		byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII"))); 
		return "Basic " + new String(tokenBase64); 
	}
	
	public Optional<usuarioLogin> autenticarUsuario(Optional<usuarioLogin> usuarioLogin){
		Optional<usuario> usuario = repository.findByUsuario(usuarioLogin.get().getUsuario()); 
		if(usuario.isPresent()) {
			if(compararSenhas(usuarioLogin.get().getSenha(), usuario.get().getSenha())){
				usuarioLogin.get().setId(usuario.get().getId()); 
				usuarioLogin.get().setNome(usuario.get().getNome()); 
				usuarioLogin.get().setUsuario(usuario.get().getUsuario());
				usuarioLogin.get().setToken(geradorBasicToken(usuarioLogin.get().getUsuario(), usuarioLogin.get().getSenha())); 
				usuarioLogin.get().setSenha(usuario.get().getSenha()); 
	
				return usuarioLogin; 
			}
		}
		return Optional.empty(); 
	}
	
	public Optional<usuario> atualizarUsuario(usuario usuario) { 

		if (repository.findById(usuario.getId()).isPresent()) {
			Optional<usuario> search = repository.findByUsuario(usuario.getUsuario());
			if (search.isPresent()) { 		
				if (search.get().getId() != usuario.getId()) 
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);
			}
			usuario.setSenha(criptografarSenha(usuario.getSenha())); 
			return Optional.of(repository.save(usuario));
		}	
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!", null);		
	}
}
