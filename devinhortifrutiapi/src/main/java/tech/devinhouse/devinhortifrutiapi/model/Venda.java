package tech.devinhouse.devinhortifrutiapi.model;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "vendas")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendager")
    @SequenceGenerator(name = "vendager", sequenceName = "venda_id_seq", allocationSize = 1)

    private Long id;

    //@ManyToOne
    //@JoinColumn(name = "id_comprador", referencedColumnName = "id")
    private Long comprador;

    //@ManyToOne
    //@JoinColumn(name = "id_vendedor", referencedColumnName = "id")
    private Long vendedor;

    @Column (name = "dt_venda")
    private LocalDateTime dataVenda;

    private String cep;

    @Column (name = "sigla_estado")
    private String siglaEstado;

    private String cidade;

    private String logradouro;

    private String bairro;

    private String complemento;

    @Column (name = "dt_entrega")
    private LocalDate dataEntrega;

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

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public String getCep() {
        return cep;
    }

    public String getSiglaEstado() {
        return siglaEstado;
    }

    public String getCidade() {
        return cidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public Boolean getVendaCancelada() {
        return vendaCancelada;
    }
}
