package br.com.fiap.soat.grupo48.pedido.application.service;


import br.com.fiap.soat.grupo48.pedido.application.domain.model.Pedido;
import br.com.fiap.soat.grupo48.pedido.application.domain.model.PedidoItem;
import br.com.fiap.soat.grupo48.pedido.application.domain.valueobject.GeradorDeNumeroSequencial;
import br.com.fiap.soat.grupo48.pedido.application.service.exception.ClienteNaoInformadoException;
import br.com.fiap.soat.grupo48.pedido.application.service.port.in.IPedidoEmAndamentoPort;
import br.com.fiap.soat.grupo48.pedido.application.service.port.out.IPedidoRepositoryGateway;
import br.com.fiap.soat.grupo48.produto.application.service.exception.ProdutoNotFoundException;
import br.com.fiap.soat.grupo48.produto.application.service.port.out.IProdutoRepositoryGateway;

import java.util.Objects;

public class PedidoEmAndamentoUseCaseImpl implements IPedidoEmAndamentoPort {
  private final IPedidoRepositoryGateway pedidoRepositoryGateway;

  private final IProdutoRepositoryGateway produtoRepositoryGateway;

  public PedidoEmAndamentoUseCaseImpl(IPedidoRepositoryGateway pedidoRepositoryGateway, IProdutoRepositoryGateway produtoRepositoryGateway) {
    this.pedidoRepositoryGateway = pedidoRepositoryGateway;
    this.produtoRepositoryGateway = produtoRepositoryGateway;
  }

  @Override
  public Pedido montaPedido(Pedido pedido) throws ProdutoNotFoundException, ClienteNaoInformadoException {
    if (Objects.isNull(pedido.getClienteId())) {
      throw new ClienteNaoInformadoException("Cliente não informado.");
    }

    // verficação de informações de pagamento
    if (Objects.nonNull(pedido.getPagamentoId())) {
      /* verifica o metodo de pagamento
        Buscar o metodo de pagamento pelo id usando chamada externa via rest
        gerar exceção = Método de pagamento não informado ou inválido
        gerar exceção = Método de pagamento não exites na nossa base
        quando mandar para a fonte pagadora mudar novamente para pendente
       */
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
    return this.pedidoRepositoryGateway.salvar(pedido);
  }

  /**
   * sinalizar que o pagamento foi efetuado
   */
  @Override
  public void efetuaPagamento() {
    // TODO document why this method is empty
  }

}
