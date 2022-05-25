package tech.devinhouse.devinhortifrutiapi.model;

import javax.persistence.*;
import java.math.BigDecimal;
import tech.devinhouse.devinhortifrutiapi.model.Venda;

@Entity(name = "item_venda")
public class ItemVenda {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_venda_ger")
    @SequenceGenerator(name = "item_venda_ger", sequenceName = "item_venda_id_seq")
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_venda", referencedColumnName = "id")
    private Venda venda;

//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "id_produto", referencedColumnName = "id")
    private Long produto;

    @Column(name = "preco_unitario")
    private BigDecimal precoUnitario;

    private Integer quantidade;
}
