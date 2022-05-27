package tech.devinhouse.devinhortifrutiapi.dto;

import java.math.BigDecimal;
import java.util.List;

public class VendaGetDto {
    private Long id;

    private String nomeCliente;

    private String cpf;

    private String email;

    private String telefone;

    private BigDecimal totalVenda;

    private String endereco;

    private List<ItemVendaGetDto> itens;

    public VendaGetDto(Long id,
                       String nomeCliente, String cpf, String email, String telefone,
                       BigDecimal totalVenda, String endereco, List<ItemVendaGetDto> itens) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.totalVenda = totalVenda;
        this.endereco = endereco;
        this.itens = itens;
    }

    public VendaGetDto() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public BigDecimal getTotalVenda() {
        return totalVenda;
    }

    public void setTotalVenda(BigDecimal totalVenda) {
        this.totalVenda = totalVenda;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public List<ItemVendaGetDto> getItens() {
        return itens;
    }

    public void setItens(List<ItemVendaGetDto> itens) {
        this.itens = itens;
    }
}
