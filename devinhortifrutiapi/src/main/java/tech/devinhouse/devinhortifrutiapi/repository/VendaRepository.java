package tech.devinhouse.devinhortifrutiapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.devinhouse.devinhortifrutiapi.model.Venda;

import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    List<Venda> findVendaByCpf(Venda vendaEntity);

    List<Venda> findUsuarioByIdUsuario(Venda vendaEntity);

    List<Venda> findVendaByIdAndCpf(Venda vendaEntity, Venda entity);
}
