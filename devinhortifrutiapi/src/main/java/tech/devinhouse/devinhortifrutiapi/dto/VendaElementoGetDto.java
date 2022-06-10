package tech.devinhouse.devinhortifrutiapi.dto;

import java.math.BigDecimal;

public class VendaElementoGetDto {

    private Long id;

    private String nomeCliente;

    private String cpf;

    private BigDecimal totalVenda;

    private Boolean vendaCancelada;

    public VendaElementoGetDto(Long id, String nomeCliente, String cpf, BigDecimal totalVenda, Boolean vendaCancelada) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.cpf = cpf;
        this.totalVenda = totalVenda;
        this.vendaCancelada = vendaCancelada;
    }

    public VendaElementoGetDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public BigDecimal getTotalVenda() {
        return totalVenda;
    }

    public void setTotalVenda(BigDecimal totalVenda) {
        this.totalVenda = totalVenda;
    }

    public Boolean getVendaCancelada() {
        return vendaCancelada;
    }

    public void setVendaCancelada(Boolean vendaCancelada) {
        this.vendaCancelada = vendaCancelada;
    }
}
