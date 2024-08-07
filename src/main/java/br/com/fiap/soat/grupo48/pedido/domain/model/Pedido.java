package br.com.fiap.soat.grupo48.pedido.domain.model;

import br.com.fiap.soat.grupo48.commons.domain.model.JsonMapper;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Pedido extends JsonMapper {

    private UUID id;
    private UUID clienteId;
    private SituacaoPedido situacao;
    private String identificacao;
    private List<PedidoItem> itens;
    private UUID pagamentoId;
    private BigDecimal total;
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

    public Pedido(UUID id, UUID clienteId, SituacaoPedido situacao, String identificacao, List<PedidoItem> itens, UUID pagamentoId, Date dataCriacao, Date dataAtualizacao) {
        this.id = id;
        this.clienteId = clienteId;
        this.situacao = situacao;
        this.identificacao = identificacao;
        this.itens = itens;
        totaliza(itens);
        this.pagamentoId = pagamentoId;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }

    public void setItens(List<PedidoItem> itens) {
        this.itens = itens;
        totaliza(itens);
    }

    private void totaliza(List<PedidoItem> itens) {
        this.total = itens.stream()
            .map(item -> BigDecimal.valueOf(item.getTotal()))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
