package tech.devinhouse.devinhortifrutiapi.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "produtos")
public class Produto {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "produtosger")
  @SequenceGenerator(name = "produtosger", sequenceName = "produtos_id_seq", allocationSize = 1)
  private Long id;

  private String nome;

  private String descricao;

  @Column(name="url_foto")
  private String urlFoto;

  @Column(name="preco_sugerido")
  private BigDecimal precoSugerido;

  @Column(name="is_ativo")
  private boolean isAtivo;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return nome;
  }

  public void setName(String name) {
    this.nome = name;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public String getUrlFoto() {
    return urlFoto;
  }

  public void setUrlFoto(String urlFoto) {
    this.urlFoto = urlFoto;
  }

  public BigDecimal getPrecoSugerido() {
    return precoSugerido;
  }

  public void setPrecoSugerido(BigDecimal precoSugerido) {
    this.precoSugerido = precoSugerido;
  }

  public boolean isAtivo() {return isAtivo;}

  public void setIsAtivo(boolean ativo) {isAtivo = ativo;}

  public Produto() {

  }

  public Produto(Long id, String nome, String descricao, String urlFoto, BigDecimal precoSugerido, boolean isAtivo) {
    this.id = id;
    this.nome = nome;
    this.descricao = descricao;
    this.urlFoto = urlFoto;
    this.precoSugerido = precoSugerido;
    this.isAtivo = isAtivo;
  }

}
