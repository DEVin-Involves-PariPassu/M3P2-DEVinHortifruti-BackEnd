package tech.devinhouse.devinhortifrutiapi.dto;

import java.math.BigDecimal;

public class ItemVendaPostDto {

    private Long idProduto;

    private BigDecimal precoUnitario;

    private Integer quantidade;

    public ItemVendaPostDto(Long idProduto, BigDecimal precoUnitario, Integer quantidade) {
        this.idProduto = idProduto;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
