package br.com.fiap.soat.grupo48.pedido.infra.adapter.db;

import br.com.fiap.soat.grupo48.pedido.domain.model.Pedido;
import br.com.fiap.soat.grupo48.pedido.domain.model.SituacaoPedido;
import br.com.fiap.soat.grupo48.pedido.application.service.port.out.IPedidoRepositoryGateway;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
public class PedidoRepositoryGateway implements IPedidoRepositoryGateway {

    private final SpringPedidoRepository springPedidoRepository;

    public PedidoRepositoryGateway(SpringPedidoRepository springPedidoRepository) {
        this.springPedidoRepository = springPedidoRepository;
    }

    @Override
    public Pedido salvar(Pedido pedido) {
        PedidoEntity pedidoEntity = null;
        if (Objects.nonNull(pedido.getId())) {
            Optional<PedidoEntity> byId = this.springPedidoRepository.findById(pedido.getId());
            if (byId.isPresent()) {
                pedidoEntity = byId.get();
                pedidoEntity.atualizar(pedido);
            }
        }

        if (Objects.isNull(pedidoEntity)) {
            pedidoEntity = new PedidoEntity(pedido);
        }
        return this.springPedidoRepository.save(pedidoEntity).toPedido();
    }

    @Override
    public Pedido atualizarSituacao(UUID id, SituacaoPedido situacao) {
        Optional<PedidoEntity> byId = this.springPedidoRepository.findById(id);
        if (byId.isPresent()) {
            PedidoEntity pedidoEntity = byId.get();
            pedidoEntity.setSituacao(situacao);
            return this.springPedidoRepository.save(pedidoEntity).toPedido();
        } else {
            return null;
        }
    }

    @Override
    public List<Pedido> buscaPedidosPorSituacoes(List<SituacaoPedido> situacoes) {
        List<PedidoEntity> bySituacaoIn = this.springPedidoRepository.findBySituacaoIn(situacoes);
        return bySituacaoIn.stream().map(PedidoEntity::toPedido).toList();
    }

    @Override
    public List<Pedido> buscaPedidosMostradosMonitorCliente() {
        List<PedidoEntity> pedidosWithoutFinalizados = this.springPedidoRepository.findPedidosWithoutFinalizados();
        return pedidosWithoutFinalizados.stream().map(PedidoEntity::toPedido).toList();
    }

    @Override
    public SituacaoPedido buscaSituacaoPedido(UUID id) {
        Optional<PedidoEntity> byId = this.springPedidoRepository.findById(id);
        if (byId.isPresent()) {
            PedidoEntity pedidoEntity = byId.get();
            return pedidoEntity.getSituacao();
        } else {
            return null;
        }
    }

    @Override
    public Pedido buscarPedidoPeloPagamento(UUID pagamentoId) {
        PedidoEntity byPagamento = this.springPedidoRepository.findByPagamentoId(pagamentoId);
        return byPagamento.toPedido();
    }

    @Override
    public Pedido buscarPeloId(UUID id) {
        return this.springPedidoRepository.findById(id).map(PedidoEntity::toPedido).orElse(null);
    }

}
