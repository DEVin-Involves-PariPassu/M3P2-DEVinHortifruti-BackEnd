package tech.devinhouse.devinhortifrutiapi.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UsuarioDto {

    @NotBlank(message = "Informe o nome")
    private String nome;

    @NotBlank(message = "O login é obrigatório")
    private String login;

    @NotBlank(message = "Informe o email")
    private String email;

    @NotNull(message = "Informe a data de nascimento no formato dd/MM/yyyy")
    private LocalDate dtNascimento;

    @NotNull(message = "Informe se o usuario é Administrador")
    private Boolean isAdmin;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public LocalDate getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(LocalDate dtNascimento) {

        this.dtNascimento = dtNascimento;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }


}
