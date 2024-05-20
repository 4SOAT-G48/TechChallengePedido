package br.com.fiap.soat.grupo48.pedido.application.service.port.in;

import br.com.fiap.soat.grupo48.pedido.application.domain.model.Pedido;
import br.com.fiap.soat.grupo48.pedido.application.service.exception.ClienteNaoInformadoException;
import br.com.fiap.soat.grupo48.pedido.application.service.exception.MetodoPagamentoInvalidoException;
import br.com.fiap.soat.grupo48.produto.application.service.exception.ProdutoNotFoundException;

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
   * Passa para o pagamento.
   */
  void efetuaPagamento();
}
