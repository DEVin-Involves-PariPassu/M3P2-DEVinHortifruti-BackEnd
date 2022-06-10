package tech.devinhouse.devinhortifrutiapi.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import tech.devinhouse.devinhortifrutiapi.model.Usuario;

import java.util.Optional;


@Repository
public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {

    Optional<Usuario> findUsuarioByLogin(String login);

    Optional<Usuario> findUsuarioByEmail(String email);

    Optional<Usuario> findUsuarioById(Long usuarioId);


}

