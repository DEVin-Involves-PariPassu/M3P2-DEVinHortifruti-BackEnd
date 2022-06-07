package tech.devinhouse.devinhortifrutiapi.service;

import org.springframework.data.jpa.domain.Specification;
import tech.devinhouse.devinhortifrutiapi.model.Produto;

public class SpecificationsProduto {

    public static Specification<Produto> nome(String nome) {
        return (root, query, criteriaBuilder) -> {
            if(nome == null) {
                return criteriaBuilder.like(root.get("nome"), "%%");
            } else return criteriaBuilder.like(root.get("nome"), "%" + nome + "%");
        };
    }
}
