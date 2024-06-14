package br.com.fiap.soat.grupo48.pedido.application.domain.model;

public enum SituacaoPedido {
    /**
     * Cliente está montando seu pedido
     */
    EM_ANDAMENTO,

    /**
     * Cliente terminou de escolher os itens
     * para seu pedido e fez seu pagamento
     */
    RECEBIDO,

    /**
     * Pedido aguarda pagamento
     */
    AGUARDANDO_PAGAMENTO,

    /**
     * Cliente pagou o pedido
     */
    PAGO,

    /**
     * Pedido falhou pagamento
     */
    FALHA_PAGAMENTO,

    /**
     * A cozinha separou o pedido para
     * começar a montagem
     */
    EM_PREPARACAO,

    /**
     * A cozinha terminou a montagem e
     * passou para o atendente fazer a entrega
     */
    PRONTO,

    /**
     * O atendente passou o pedido para a
     * faze de entrega, o cliente pode vir buscar o pedido.
     */
    EM_ENTREGA,

    /**
     * pedido entregue ao cliente
     */
    FINALIZADO
}
