package tech.devinhouse.devinhortifrutiapi.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tech.devinhouse.devinhortifrutiapi.model.Comprador;

import java.util.Optional;

@Repository
public interface CompradorRepository extends CrudRepository<Comprador, Long>, JpaSpecificationExecutor<Comprador> {

    Optional<Comprador> findByCpf(String cpf);

    Optional<Comprador> findByEmail(String email);
}
