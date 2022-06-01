package tech.devinhouse.devinhortifrutiapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import tech.devinhouse.devinhortifrutiapi.model.Venda;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long>, JpaSpecificationExecutor<Venda> {

    Page<Venda> findAll(Specification<Venda> specification, Pageable pageable);
    Page<Venda> findByVendedor(Long vendedor, Specification<Venda> specification, Pageable pageable);
}
