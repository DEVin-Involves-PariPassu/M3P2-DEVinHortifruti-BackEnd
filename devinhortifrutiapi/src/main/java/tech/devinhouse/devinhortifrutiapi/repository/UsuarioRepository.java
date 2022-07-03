package tech.devinhouse.devinhortifrutiapi.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import tech.devinhouse.devinhortifrutiapi.model.Usuario;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.*;

import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaSpecificationExecutor<Usuario>, PagingAndSortingRepository<Usuario, Long> {

    Optional<Usuario> findUsuarioByLogin(String login);

    Optional<Usuario> findUsuarioByEmail(String email);

    Optional<Usuario> findUsuarioById(Long usuarioId);

    Page<Usuario> findAll(Specification spec , Pageable pageable);
}

