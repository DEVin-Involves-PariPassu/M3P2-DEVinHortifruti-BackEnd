package tech.devinhouse.devinhortifrutiapi.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


public class LoginDTO {

        private String login;
        private String senha;

    public LoginDTO(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public UsernamePasswordAuthenticationToken converter(){
            return new UsernamePasswordAuthenticationToken(login, senha);
        }

    public LoginDTO() {}
}
