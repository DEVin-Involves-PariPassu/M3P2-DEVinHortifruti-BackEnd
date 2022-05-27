package tech.devinhouse.devinhortifrutiapi.dto;

import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ProdutoDTO {

    private Long id;

     @NotNull (message = "Nome do produto é requerido")
    private String nome;

     @NotNull(message = "Descrição do produto é requerida")
    private String descricao;

      @NotNull(message = "URL da imagem do produto é requerida")
    private String urlFoto;

     @NotNull(message = "Preço do produto é requerido")
    private BigDecimal precoAtual;

     @NotNull(message = "Status do produto é requerido")
    private Boolean isActive;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public BigDecimal getPrecoAtual() {
        return precoAtual;
    }

    public void setPrecoAtual(BigDecimal precoAtual) {
        this.precoAtual = precoAtual;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

}
