package tech.devinhouse.devinhortifrutiapi.dto;

import com.sun.istack.NotNull;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class ItemVendaPostDto {

    @NotNull
    private Long idProduto;

    @NotNull
    private BigDecimal precoUnitario;

    @NotNull
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
