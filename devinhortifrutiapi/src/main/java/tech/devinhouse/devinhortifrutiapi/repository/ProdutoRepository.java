package tech.devinhouse.devinhortifrutiapi.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.devinhouse.devinhortifrutiapi.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {


}
