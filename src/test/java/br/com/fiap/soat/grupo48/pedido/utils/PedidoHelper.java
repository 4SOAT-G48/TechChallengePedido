package br.com.fiap.soat.grupo48.pedido.utils;

import br.com.fiap.soat.grupo48.pedido.domain.model.Pedido;
import br.com.fiap.soat.grupo48.pedido.domain.model.PedidoItem;
import br.com.fiap.soat.grupo48.pedido.domain.model.SituacaoPedido;
import br.com.fiap.soat.grupo48.produto.domain.model.Produto;
import br.com.fiap.soat.grupo48.produto.utils.ProdutoHelper;
import br.com.fiap.soat.grupo48.utils.FormatoHelper;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public abstract class PedidoHelper extends FormatoHelper {

    public static Pedido gerarPedido(UUID clienteId) {
        return Pedido.builder()
            .clienteId(clienteId)
            .situacao(SituacaoPedido.EM_ANDAMENTO)
            .identificacao("TE000001")
            .itens(gerarListaItens())
            .dataCriacao(new Date())
            .dataAtualizacao(new Date())
            .build();
    }

    public static List<PedidoItem> gerarListaItens() {

        Produto produto1 = ProdutoHelper.gerarProduto();
        PedidoItem pedidoItem1 = new PedidoItem(null, produto1, 1, produto1.getPreco(), "");

        Produto produto2 = ProdutoHelper.gerarProdutoAcompanhamento();
        PedidoItem pedidoItem2 = new PedidoItem(null, produto2, 1, produto2.getPreco(), "");

        Produto produto3 = ProdutoHelper.gerarProdutoBebida();
        PedidoItem pedidoItem3 = new PedidoItem(null, produto3, 1, produto3.getPreco(), "");

        return List.of(
            pedidoItem1,
            pedidoItem2,
            pedidoItem3
        );
    }
}
