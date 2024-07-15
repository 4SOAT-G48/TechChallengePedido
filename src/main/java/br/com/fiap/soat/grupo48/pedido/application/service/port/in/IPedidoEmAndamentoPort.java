package br.com.fiap.soat.grupo48.pedido.application.service.port.in;

import br.com.fiap.soat.grupo48.pedido.application.service.exception.ClienteNaoInformadoException;
import br.com.fiap.soat.grupo48.pedido.application.service.exception.MetodoPagamentoInvalidoException;
import br.com.fiap.soat.grupo48.pedido.domain.model.Pedido;
import br.com.fiap.soat.grupo48.produto.application.service.exception.ProdutoNotFoundException;

import java.util.UUID;

public interface IPedidoEmAndamentoPort {
    /**
     * Faze de montagem do pedido.
     * Pode ser desde a adição do primeiro item
     * até ele escolher concluir o pedido.
     *
     * @param pedido pedido com os dados a serem salvos
     * @return pedido com os dados conforme salvos
     */
    Pedido montaPedido(Pedido pedido) throws MetodoPagamentoInvalidoException, ProdutoNotFoundException, ClienteNaoInformadoException;

    /**
     * Registra que o pagamento foi efetuado
     *
     * @param pedidoId    id do pedido
     * @param pagamentoId id do pagamento
     */
    void pagamentoEfetuado(UUID pedidoId, UUID pagamentoId);
}
