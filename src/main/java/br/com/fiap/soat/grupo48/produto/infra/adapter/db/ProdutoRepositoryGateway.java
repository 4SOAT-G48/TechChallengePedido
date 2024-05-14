package br.com.fiap.soat.grupo48.produto.infra.adapter.db;

import br.com.fiap.soat.grupo48.produto.application.domain.model.Categoria;
import br.com.fiap.soat.grupo48.produto.application.domain.model.Produto;
import br.com.fiap.soat.grupo48.produto.application.domain.model.SituacaoProduto;
import br.com.fiap.soat.grupo48.produto.application.service.exception.ProdutoNotFoundException;
import br.com.fiap.soat.grupo48.produto.application.service.port.out.IProdutoRepositoryGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementação de classe para manutenção do produto.
 *
 * @author andrelaus
 */
@Component
public class ProdutoRepositoryGateway implements IProdutoRepositoryGateway {

  private final SpringProdutoRepository springProdutoRepository;

  public ProdutoRepositoryGateway(SpringProdutoRepository springProdutoRepository) {
    this.springProdutoRepository = springProdutoRepository;
  }

  @Override
  public List<Produto> buscarTodos() {
    List<ProdutoEntity> produtoEntity = this.springProdutoRepository.findAll();
    return produtoEntity.stream().map(ProdutoEntity::toProduto).toList();
  }

  @Override
  public Page<Produto> buscarTodos(Pageable pageable) {
    Page<ProdutoEntity> produtoEntity = this.springProdutoRepository.findAll(pageable);
    return produtoEntity.map(ProdutoEntity::toProduto);
  }

  @Override
  public Produto buscarPeloCodigo(UUID codigo) throws ProdutoNotFoundException {
    Optional<ProdutoEntity> produtoEntityOptional = this.springProdutoRepository.findById(codigo);
    if (produtoEntityOptional.isEmpty()) {
      throw new ProdutoNotFoundException("Produto não encontrado");
    }
    ProdutoEntity produtoEntity = produtoEntityOptional.get();
    return produtoEntity.toProduto();
  }

  @Override
  public Produto salvar(Produto produto) throws ProdutoNotFoundException {
    ProdutoEntity produtoEntity;
    if (Objects.isNull(produto.getCodigo())) {
      produtoEntity = new ProdutoEntity(produto);
    } else {
      var produtoEncontrado = this.springProdutoRepository.findById(produto.getCodigo());
      if (produtoEncontrado.isEmpty()) {
        throw new ProdutoNotFoundException("Produto não encontrado");
      }
      produtoEntity = produtoEncontrado.get();
      produtoEntity.atualizar(produto);
    }

    return this.springProdutoRepository.save(produtoEntity).toProduto();
  }

  @Override
  public boolean excluir(UUID codigo) throws ProdutoNotFoundException {
    Optional<ProdutoEntity> produtoEntityOptional = this.springProdutoRepository.findById(codigo);
    if (produtoEntityOptional.isEmpty()) {
      throw new ProdutoNotFoundException("Não foi possível excluir o produto");
    }
    this.springProdutoRepository.deleteById(codigo);
    return true;
  }


  @Override
  public List<Produto> buscarPorCategoria(Categoria categoria) {
    List<ProdutoEntity> produtoEntity = this.springProdutoRepository.findByCategoria(categoria);
    return produtoEntity.stream().map(ProdutoEntity::toProduto).collect(Collectors.toList());
  }

  @Override
  public List<Produto> buscarPorCategoriaESituacao(Categoria categoria, SituacaoProduto situacao) {
    List<ProdutoEntity> produtoEntity = this.springProdutoRepository.findByCategoriaAndSituacao(categoria, situacao);
    return produtoEntity.stream().map(ProdutoEntity::toProduto).collect(Collectors.toList());
  }
}
