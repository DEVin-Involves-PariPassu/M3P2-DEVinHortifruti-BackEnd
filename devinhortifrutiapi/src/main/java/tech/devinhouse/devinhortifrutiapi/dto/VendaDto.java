package tech.devinhouse.devinhortifrutiapi.dto;

import tech.devinhouse.devinhortifrutiapi.dto.ItemVendaDto;
import java.math.BigDecimal;

public class VendaDto {
    private Long id;

    private Long comprador;

    private Long vendedor;

    private String dataVenda;

    private BigDecimal totalVenda;

    private String cep;

    private String siglaEstado;

    private String cidade;

    private String logradouro;

    private String bairro;

    private String complemento;

    private String dataEntrega;

    private List<ItemVendaDto> itens;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getComprador() {
        return comprador;
    }

    public void setComprador(Long comprador) {
        this.comprador = comprador;
    }

    public Long getVendedor() {
        return vendedor;
    }

    public void setVendedor(Long vendedor) {
        this.vendedor = vendedor;
    }

    public String getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }

    public BigDecimal getTotalVenda() {
        return totalVenda;
    }

    public void setTotalVenda(BigDecimal totalVenda) {
        this.totalVenda = totalVenda;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getSiglaEstado() {
        return siglaEstado;
    }

    public void setSiglaEstado(String siglaEstado) {
        this.siglaEstado = siglaEstado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(String dataEntrega) {
        this.dataEntrega = dataEntrega;
    }
}
