package br.com.fiap.soat.grupo48.pedido.infra.adapter.db;

import br.com.fiap.soat.grupo48.pedido.application.domain.model.PedidoItem;
import br.com.fiap.soat.grupo48.produto.infra.adapter.db.ProdutoEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Entity
@Table(name = "pedido_itens")
public class PedidoItemEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @Setter
  @ManyToOne
  @JoinColumn(name = "pedido_id")
  private PedidoEntity pedido;

  @ManyToOne
  @JoinColumn(name = "produto_id")
  private ProdutoEntity produto;

  private Integer quantidade;
  private Double precoUnitario;

  private String observacao;

  public PedidoItemEntity() {
  }

  public PedidoItemEntity(PedidoItem pedidoItem) {
    this.atualizar(pedidoItem);
  }

  public PedidoItem toPedidoItem() {
    return new PedidoItem(this.id, this.produto.toProduto(), this.quantidade, this.precoUnitario, this.observacao);
  }

  public void atualizar(PedidoItem pedidoItem) {
    this.produto = new ProdutoEntity(pedidoItem.getProduto());
    this.quantidade = pedidoItem.getQuantidade();
    this.precoUnitario = pedidoItem.getPrecoUnitario();
    this.observacao = pedidoItem.getObservacao();
  }

}
