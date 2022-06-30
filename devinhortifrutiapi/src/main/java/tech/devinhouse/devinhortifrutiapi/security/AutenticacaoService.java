package tech.devinhouse.devinhortifrutiapi.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.devinhouse.devinhortifrutiapi.repository.UsuarioRepository;

@Service
public class AutenticacaoService implements UserDetailsService {
    private final UsuarioRepository repository;

    public AutenticacaoService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String usuarioNome) throws UsernameNotFoundException {
        return (UserDetails) repository.findUsuarioByLogin(usuarioNome).orElseThrow();
    }
}
