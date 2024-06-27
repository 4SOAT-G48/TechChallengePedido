package br.com.fiap.soat.grupo48.pedido.infra.adapter.db;

import br.com.fiap.soat.grupo48.pedido.domain.model.SituacaoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpringPedidoRepository extends JpaRepository<PedidoEntity, UUID> {

    /**
     * A lista de pedidos retorá ordenados com a seguinte regra:
     * <ul>
     *     <li>Ordem da situação: Finalizado > Em Entrega > Pronto > Em Preparação > Recebido > Em Andamento</li>
     *     <li>Ordem por data de criação: Pedidos mais antigos primeiro e mais novos depois</li>
     *     <li>Filtro: Pedidos com as situações passadas por parâmetro {@code situacoes}</li>
     * <ul/>
     *
     * @param situacoes
     * @return lista de pedidos ordenados
     */
    @Query("SELECT p FROM PedidoEntity p " +
        "WHERE p.situacao IN :situacoes " +
        "ORDER BY " +
        " CASE WHEN p.situacao = 'FINALIZADO' THEN 1 " +
        " WHEN p.situacao = 'EM_ENTREGA' THEN 2 " +
        " WHEN p.situacao = 'PRONTO' THEN 3 " +
        " WHEN p.situacao = 'EM_PREPARACAO' THEN 4 " +
        " WHEN p.situacao = 'RECEBIDO' THEN 5 " +
        " WHEN p.situacao = 'EM_ANDAMENTO' THEN 6 END, " +
        " p.dataCriacao DESC")
    List<PedidoEntity> findBySituacaoIn(@Param("situacoes") List<SituacaoPedido> situacoes);


    /**
     * A lista de pedidos retorá ordenados com a seguinte regra:
     * <ul>
     *     <li>Ordem da situação: Pronto > Em Preparação > Recebido</li>
     *     <li>Ordem por data de criação: Pedidos mais antigos primeiro e mais novos depois</li>
     *     <li>Filtro: Pedidos diferentes de Situação FINALIZADO</li>
     * <ul/>
     *
     * @return lista de pedidos ordenados
     */
    @Query("SELECT p FROM PedidoEntity p " +
        "WHERE p.situacao NOT IN ('FINALIZADO', 'PRONTO', 'EM_ANDAMENTO') " +
        "ORDER BY " +
        " CASE WHEN p.situacao = 'EM_ENTREGA' THEN 1 " +
        " WHEN p.situacao = 'EM_PREPARACAO' THEN 2 " +
        " WHEN p.situacao = 'RECEBIDO' THEN 3 END, " +
        " p.dataCriacao DESC")
    List<PedidoEntity> findPedidosWithoutFinalizados();

    PedidoEntity findByPagamentoId(UUID pagamentoId);
}
