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

    public static Specification<Usuario> login(String login){
        return (root, query, criteriaBuilder) -> {
            if(login == null) {
                return criteriaBuilder.like(root.get("login"), "%%");
            } else return criteriaBuilder.like(root.get("login"), "%" + login + "%");
        };
    }

    public static Specification<Usuario>totalDePaginas(Integer totalDePaginas){
        return (root, query, criteriaBuilder) -> {
            if(totalDePaginas == null) {
                return criteriaBuilder.like(root.get("totalDePaginas"), "%%");
            } else return criteriaBuilder.like(root.get("totalDePaginas"), "%" + totalDePaginas + "%");
        };
    }

    public static Specification<Usuario>totalPorPaginas(Integer totalPorPaginas){
        return (root, query, criteriaBuilder) -> {
            if(totalPorPaginas == null) {
                return criteriaBuilder.like(root.get("totalPorPagina"), "%%");
            } else return criteriaBuilder.like(root.get("totalPorPagina"), "%" + totalPorPaginas + "%");
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
