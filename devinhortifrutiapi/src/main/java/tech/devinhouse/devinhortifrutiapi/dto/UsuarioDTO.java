package tech.devinhouse.devinhortifrutiapi.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UsuarioDTO {

    @NotNull(message = "Informe o ID")
    private Long id;

    @NotBlank(message = "Informe o nome")
    private String nome;

    @NotBlank(message = "O login é obrigatório")
    private String login;

    @NotBlank(message = "Informe o email")
    private String email;

    @NotBlank(message = "Informe a data de nascimento no formato dd/MM/yyyy")
    private String dtNascimento;

    @NotNull(message = "Informe se o usuario é Administrador")
    private Boolean isAdmin;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(String dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean admin) {
        isAdmin = admin;
    }

}

