package tech.devinhouse.devinhortifrutiapi.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.devinhouse.devinhortifrutiapi.model.Usuario;

import java.util.List;
import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    Optional<Usuario> findByLoginAndSenha(String login, String senha);

    Optional<Usuario> findUsuarioByLogin(String login);

    Optional<Usuario> findUsuarioByNome(String nome);

    Optional<Usuario> findUsuarioById(Long usuarioId);
}
