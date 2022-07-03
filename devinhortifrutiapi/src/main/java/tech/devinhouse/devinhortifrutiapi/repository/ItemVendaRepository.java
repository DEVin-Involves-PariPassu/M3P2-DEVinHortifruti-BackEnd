package tech.devinhouse.devinhortifrutiapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.devinhouse.devinhortifrutiapi.model.ItemVenda;
import tech.devinhouse.devinhortifrutiapi.model.Venda;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {
    // @Query("SELECT * FROM item_venda JOIN produtos ON item_venda.id_produto = produtos.id", nativeQuery = true)
            List<ItemVenda> findAll();
    //List<ItemVenda> findByProduto(Product produto);
    List<ItemVenda> findByVenda (Venda venda);

}
