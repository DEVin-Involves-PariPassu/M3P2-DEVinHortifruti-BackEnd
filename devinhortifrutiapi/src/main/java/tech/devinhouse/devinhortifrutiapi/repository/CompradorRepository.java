package tech.devinhouse.devinhortifrutiapi.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.devinhouse.devinhortifrutiapi.model.Compradores;

@Repository
public interface CompradorRepository extends JpaRepository<Compradores, Long> {

}
