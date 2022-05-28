package tech.devinhouse.devinhortifrutiapi.dto;

import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CompradorDTO {
    private Long id;

    @NotBlank(message = "Campo Nome obrigatório!")
    @NotNull (message = "Campo Nome obrigatório!")
    private String nome;

    @NotBlank(message = "Campo CPF obrigatório!")
    @NotNull(message = "Campo CPF obrigatório!")
    @CPF(message = "CPF inválido!")
    private String cpf;

    @NotBlank(message = "Campo E-mail obrigatório!")
    @NotNull(message = "Campo E-mail obrigatório!")
    @Email(message = "E-mail inválido!")
    private String email;

    @NotBlank(message = "Campo Telefone obrigatório!")
    @NotNull(message = "Campo Telefone obrigatório!")
    private String telefone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}