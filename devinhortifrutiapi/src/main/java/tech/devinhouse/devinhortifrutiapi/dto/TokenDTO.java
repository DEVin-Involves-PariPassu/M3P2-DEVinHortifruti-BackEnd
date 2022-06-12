package tech.devinhouse.devinhortifrutiapi.dto;

public class TokenDTO {
    private String token;
    private String tipo;

    private Boolean isAdmin;

    public TokenDTO(){

    }
    public TokenDTO(String token, String tipo, Boolean isAdmin) {
        this.token = token;
        this.tipo = tipo;
        this.isAdmin = isAdmin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
