package tech.devinhouse.devinhortifrutiapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.devinhouse.devinhortifrutiapi.model.Venda;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
}
