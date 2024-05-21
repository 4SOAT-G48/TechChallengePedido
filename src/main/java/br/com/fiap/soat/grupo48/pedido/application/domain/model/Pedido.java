package br.com.fiap.soat.grupo48.pedido.application.domain.model;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Pedido {

  private UUID id;
  private UUID clienteId;
  private SituacaoPedido situacao;
  private String identificacao;
  private List<PedidoItem> itens;
  private UUID pagamentoId;
  private Date dataCriacao;
  private Date dataAtualizacao;

  public Pedido(UUID id, UUID clienteId, SituacaoPedido situacao, String identificacao, List<PedidoItem> itens, UUID pagamentoId) {
    this.id = id;
    this.clienteId = clienteId;
    this.situacao = situacao;
    this.identificacao = identificacao;
    this.itens = itens;
    this.pagamentoId = pagamentoId;
  }

}
