package br.com.fiap.soat.grupo48.produto.infra.adapter.db;

import br.com.fiap.soat.grupo48.produto.application.domain.model.Categoria;
import br.com.fiap.soat.grupo48.produto.application.domain.model.SituacaoProduto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpringProdutoRepository extends JpaRepository<ProdutoEntity, UUID> {
  List<ProdutoEntity> findByCategoria(Categoria categoria);

  List<ProdutoEntity> findByCategoriaAndSituacao(Categoria categoria, SituacaoProduto situacao);

  @Query(value = "SELECT p FROM ProdutoEntity p ORDER BY p.codigo")
  Page<ProdutoEntity> findProdutos(Pageable pageable);
}
