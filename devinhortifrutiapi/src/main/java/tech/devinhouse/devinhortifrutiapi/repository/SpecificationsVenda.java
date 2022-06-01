package tech.devinhouse.devinhortifrutiapi.repository;

import org.springframework.data.jpa.domain.Specification;
import tech.devinhouse.devinhortifrutiapi.model.Comprador;
import tech.devinhouse.devinhortifrutiapi.model.Venda;

import javax.persistence.criteria.Join;

public class SpecificationsVenda {

    public static Specification<Venda> nome(String nome){
        return (root, query, criteriaBuilder) -> {
            if(nome == null) {
                return criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
            } else {
                return criteriaBuilder.like(root.join("comprador").get("nome"), "%" + nome + "%");
            }
        };
    }

    public static Specification<Venda> cpf(String cpf){
        return (root, query, criteriaBuilder) -> {
            if(cpf == null) {
                return criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
            } else {
                return criteriaBuilder.like(root.join("comprador").get("cpf"), "%" +  cpf + "%");
            }
        };
    }
}
