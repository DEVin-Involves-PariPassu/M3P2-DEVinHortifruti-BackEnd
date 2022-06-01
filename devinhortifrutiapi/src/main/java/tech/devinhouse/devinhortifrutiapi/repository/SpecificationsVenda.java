package tech.devinhouse.devinhortifrutiapi.repository;

import org.springframework.data.jpa.domain.Specification;
import tech.devinhouse.devinhortifrutiapi.model.Venda;

public class SpecificationsVenda {

    public static Specification<Venda> nome(String nome){
        return (root, query, criteriaBuilder) -> {
            if(nome == null) {
                return criteriaBuilder.like(root.join("compradores").get("nome"), "%%");
            } else return criteriaBuilder.like(root.join("compradores").get("nome"), "%" + nome + "%");
        };
    }

    public static Specification<Venda> cpf(String cpf){
        return (root, query, criteriaBuilder) -> {
            if(cpf == null) {
                return criteriaBuilder.like(root.join("compradores").get("cpf"), "%%");
            } else return criteriaBuilder.like(root.join("compradores").get("cpf"), "%" + cpf + "%");
        };
    }
}
