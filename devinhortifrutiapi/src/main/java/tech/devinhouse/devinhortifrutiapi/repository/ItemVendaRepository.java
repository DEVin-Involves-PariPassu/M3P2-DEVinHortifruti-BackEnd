package tech.devinhouse.devinhortifrutiapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.devinhouse.devinhortifrutiapi.model.ItemVenda;
import tech.devinhouse.devinhortifrutiapi.model.Venda;

import java.util.List;

@Repository
public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {

    //List<ItemVenda> findByProduto(Product produto);
    List<ItemVenda> findByVenda (Venda venda);

}
