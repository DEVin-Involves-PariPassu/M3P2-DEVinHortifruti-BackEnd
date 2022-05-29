package tech.devinhouse.devinhortifrutiapi.service;

import org.springframework.data.jpa.domain.Specification;
import tech.devinhouse.devinhortifrutiapi.model.Usuario;

import java.time.LocalDate;

public class SpecificationsUsuario {

    public static Specification<Usuario> nome(String nome){
        return (root, query, criteriaBuilder) -> {
            if(nome == null) {
                return criteriaBuilder.like(root.get("nome"), "%%");
            } else return criteriaBuilder.like(root.get("nome"), "%" + nome + "%");
        };
    }

    public static Specification<Usuario> dtNascimentoMin(LocalDate dtNascimentoMin){
        return (root, query, criteriaBuilder) -> {
            if(dtNascimentoMin == null) {
                return criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
            } else return criteriaBuilder.greaterThan(root.get("dtNascimento"),dtNascimentoMin);
        };
    }

    public static Specification<Usuario> dtNascimentoMax(LocalDate dtNascimentoMax){
        return (root, query, criteriaBuilder) -> {
            if(dtNascimentoMax == null) {
                return criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
            } else return criteriaBuilder.lessThan(root.get("dtNascimento"),dtNascimentoMax);
        };
    }
}
