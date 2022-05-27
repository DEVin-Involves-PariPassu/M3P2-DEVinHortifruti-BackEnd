package tech.devinhouse.devinhortifrutiapi.dto;

import java.util.List;

public class VendaPostDto {

    private Long idComprador;

    private Long idVendedor;

    private String cep;

    private String siglaEstado;

    private String cidade;

    private String logradouro;

    private String bairro;

    private String complemento;

    private String dataEntrega;

    private List<ItemVendaPostDto> itens;

    public VendaPostDto(Long idComprador, Long idVendedor,
                        String cep, String siglaEstado, String cidade, String logradouro, String bairro, String complemento,
                        String dataEntrega, List<ItemVendaPostDto> itens) {
        this.idComprador = idComprador;
        this.idVendedor = idVendedor;
        this.cep = cep;
        this.siglaEstado = siglaEstado;
        this.cidade = cidade;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.complemento = complemento;
        this.dataEntrega = dataEntrega;
        this.itens = itens;
    }

    public VendaPostDto() {
    }

    public Long getIdComprador() {
        return idComprador;
    }

    public void setIdComprador(Long idComprador) {
        this.idComprador = idComprador;
    }

    public Long getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Long idVendedor) {
        this.idVendedor = idVendedor;
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

    public List<ItemVendaPostDto> getItens() {
        return itens;
    }

    public void setItens(List<ItemVendaPostDto> itens) {
        this.itens = itens;
    }
}
