package tech.devinhouse.devinhortifrutiapi.model;


import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuarioger")
    @SequenceGenerator(name = "usuarioger", sequenceName = "usuario_id_seq", allocationSize = 1)
    private Long id;

    private String login;
    private String senha;
    private String nome;
    private String email;
    private LocalDate dtNascimento;
    private boolean admin;

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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Usuario(Long id, String login, String senha, String nome, String email, LocalDate dtNascimento, boolean admin) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.email = email;
        this.dtNascimento = dtNascimento;
        this.admin = admin;
    }

    public Usuario() {
    }
}
