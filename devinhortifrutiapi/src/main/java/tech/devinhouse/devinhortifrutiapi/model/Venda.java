package tech.devinhouse.devinhortifrutiapi.model;

import javax.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "vendas")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendager")
    @SequenceGenerator(name = "vendager", sequenceName = "venda_id_seq", allocationSize = 1)

    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_comprador", referencedColumnName = "id")
    private Long comprador;

    @ManyToOne
    @JoinColumn(name = "id_vendedor", referencedColumnName = "id")
    private Long vendedor;

    @Column (name = "dt_venda")
    private LocalDateTime dataVenda;

    @Column(name = "total_venda")
    private BigDecimal totalVenda;

    private String cep;

    @Column (name = "sigla_estado")
    private String siglaEstado;

    private String cidade;

    private String logradouro;

    private String bairro;

    private String complemento;

    @Column (name = "dt_entrega")
    private LocalDate dataEntrega;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "venda", fetch = FetchType.EAGER)
    private List<ItemVenda> itens;

    @Column(name = "venda_cancelada")
    private Boolean vendaCancelada;

    public Long getId() {
        return id;
    }

    public Long getComprador() {
        return comprador;
    }

    public Long getVendedor() {
        return vendedor;
    }

    public void setComprador(Long comprador) {
        this.comprador = comprador;
    }

    public void setVendedor(Long vendedor) {
        this.vendedor = vendedor;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public BigDecimal getTotalVenda() {
        return totalVenda;
    }

    public void setTotalVenda(BigDecimal totalVenda) {
        this.totalVenda = totalVenda;
    }

    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setSiglaEstado(String siglaEstado) {
        this.siglaEstado = siglaEstado;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }

    public void setVendaCancelada(Boolean vendaCancelada) {
        this.vendaCancelada = vendaCancelada;
    }
}
