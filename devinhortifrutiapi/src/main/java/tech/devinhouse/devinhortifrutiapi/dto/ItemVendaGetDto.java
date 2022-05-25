package tech.devinhouse.devinhortifrutiapi.dto;

import java.math.BigDecimal;

public class ItemVendaGetDto {

    private String urlFoto;

    private String nome;

    private Integer quantidade;

    private BigDecimal subtotal;

    public ItemVendaGetDto(String urlFoto, String nome, Integer quantidade, BigDecimal subtotal) {
        this.urlFoto = urlFoto;
        this.nome = nome;
        this.quantidade = quantidade;
        this.subtotal = subtotal;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
