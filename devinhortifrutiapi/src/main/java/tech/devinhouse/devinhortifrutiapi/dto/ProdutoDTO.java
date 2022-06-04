package tech.devinhouse.devinhortifrutiapi.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProdutoDTO {

    private Long id;

    @NotNull (message = "O nome do produto é requerido.")
    @NotBlank (message = "O nome do produto é requerido.")
    private String nome;

    @NotNull(message = "A descrição do produto é requerida.")
    @NotBlank(message = "A descrição do produto é requerida.")
    private String descricao;

    @NotNull(message = "A URL da imagem do produto é requerida.")
    private String urlFoto;

    @NotNull(message = "O preço do produto é requerido.")
    private BigDecimal precoSugerido;

    @NotNull(message = "O status do produto é requerido.")
    private Boolean isAtivo;

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

    public BigDecimal getPrecoSugerido() {
        return precoSugerido;
    }

    public void setPrecoSugerido(BigDecimal precoSugerido) {
        this.precoSugerido = precoSugerido;}

    public Boolean getIsAtivo() {return isAtivo;}

    public void setIsAtivo(Boolean ativo) {isAtivo = ativo;}

    @Override
    public String toString() {
        return "ProdutoDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", urlFoto='" + urlFoto + '\'' +
                ", precoSugerido=" + precoSugerido +
                ", isAtivo=" + isAtivo +
                '}';
    }
}
