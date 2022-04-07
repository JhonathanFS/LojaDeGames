package org.generation.gamestore.security;

import java.util.Optional;

import org.generation.gamestore.model.usuario;
import org.generation.gamestore.repository.usuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired 
    private usuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) 
    throws UsernameNotFoundException { 
        Optional<usuario> usuario = usuarioRepository.findByUsuario(userName); 
        usuario.orElseThrow	
        (() -> new UsernameNotFoundException(userName + "  não foi encontrado.")); 

        return usuario.map(UserDetailsImpl::new).get();	
    }
}
