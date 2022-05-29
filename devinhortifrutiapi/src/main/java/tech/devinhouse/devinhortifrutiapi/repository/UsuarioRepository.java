package tech.devinhouse.devinhortifrutiapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;
import tech.devinhouse.devinhortifrutiapi.model.Usuario;

import java.util.Collection;
import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {

    Optional<Usuario> findUsuarioByLogin(String login);

    Optional<Usuario> findUsuarioByEmail(String email);

    Optional<Usuario> findUsuarioById(Long usuarioId);
}
