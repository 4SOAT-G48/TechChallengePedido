package br.com.fiap.soat.grupo48.pedido.application.service;


import br.com.fiap.soat.grupo48.pedido.application.service.exception.ClienteNaoInformadoException;
import br.com.fiap.soat.grupo48.pedido.application.service.port.in.IPedidoEmAndamentoPort;
import br.com.fiap.soat.grupo48.pedido.application.service.port.out.IPedidoPublishQueueAdapter;
import br.com.fiap.soat.grupo48.pedido.application.service.port.out.IPedidoRepositoryGateway;
import br.com.fiap.soat.grupo48.pedido.domain.model.Pedido;
import br.com.fiap.soat.grupo48.pedido.domain.model.PedidoItem;
import br.com.fiap.soat.grupo48.pedido.domain.valueobject.GeradorDeNumeroSequencial;
import br.com.fiap.soat.grupo48.produto.application.service.exception.ProdutoNotFoundException;
import br.com.fiap.soat.grupo48.produto.application.service.port.out.IProdutoRepositoryGateway;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

public class PedidoEmAndamentoUseCaseImpl implements IPedidoEmAndamentoPort {
    private final IPedidoRepositoryGateway pedidoRepositoryGateway;

    private final IProdutoRepositoryGateway produtoRepositoryGateway;
    private final IPedidoPublishQueueAdapter pedidoPublishQueueAdapter;

    public PedidoEmAndamentoUseCaseImpl(IPedidoRepositoryGateway pedidoRepositoryGateway, IProdutoRepositoryGateway produtoRepositoryGateway, IPedidoPublishQueueAdapter pedidoPublishQueueAdapter) {
        this.pedidoRepositoryGateway = pedidoRepositoryGateway;
        this.produtoRepositoryGateway = produtoRepositoryGateway;
        this.pedidoPublishQueueAdapter = pedidoPublishQueueAdapter;
    }

    @Override
    @Transactional
    public Pedido montaPedido(Pedido pedido) throws ProdutoNotFoundException, ClienteNaoInformadoException {
        if (Objects.isNull(pedido.getClienteId())) {
            throw new ClienteNaoInformadoException("Cliente não informado.");
        }

        if (Objects.nonNull(pedido.getIdentificacao())
            && !pedido.getIdentificacao().isEmpty()) {
            pedido.setIdentificacao(pedido.getIdentificacao());
        } else {
            pedido.setIdentificacao(GeradorDeNumeroSequencial.getInstance().proximoNumero());
        }

        //para cada item do pedido, busca o produto e seta no item
        for (PedidoItem pedidoItem : pedido.getItens()) {
            pedidoItem.setProduto(this.produtoRepositoryGateway.buscarPeloId(pedidoItem.getProduto().getId()));
        }
        Pedido pedidoSalvo = this.pedidoRepositoryGateway.salvar(pedido);

        this.pedidoPublishQueueAdapter.publishRegistrado(pedidoSalvo.toJson());

        return pedidoSalvo;
    }

    /**
     * sinalizar que o pagamento foi efetuado
     */
    @Override
    public void pagamentoEfetuado(UUID pedidoId, UUID pagamentoId) {
        Pedido pedido = this.pedidoRepositoryGateway.buscarPeloId(pedidoId);
        pedido.setPagamentoId(pagamentoId);
    }

}
