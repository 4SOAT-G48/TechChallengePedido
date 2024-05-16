package br.com.fiap.soat.grupo48.produto.application.service;

import br.com.fiap.soat.grupo48.produto.application.domain.model.Categoria;
import br.com.fiap.soat.grupo48.produto.application.domain.model.Produto;
import br.com.fiap.soat.grupo48.produto.application.domain.model.SituacaoProduto;
import br.com.fiap.soat.grupo48.produto.application.service.exception.ProdutoNotFoundException;
import br.com.fiap.soat.grupo48.produto.application.service.port.in.IProdutoPort;
import br.com.fiap.soat.grupo48.produto.application.service.port.out.IProdutoRepositoryGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ManutecaoProdutoUsecaseImpl implements IProdutoPort {

  private final IProdutoRepositoryGateway produtoRepository;

  public ManutecaoProdutoUsecaseImpl(IProdutoRepositoryGateway produtoRepository) {
    this.produtoRepository = produtoRepository;
  }

  @Override
  public List<Produto> buscarProdutos() {
    return this.produtoRepository.buscarTodos();
  }

  @Override
  public Produto buscarPeloCodigo(UUID codigo) throws ProdutoNotFoundException {
    return this.produtoRepository.buscarPeloCodigo(codigo);
  }

  @Override
  public Produto criarProduto(Produto produto) throws ProdutoNotFoundException {
    return this.produtoRepository.salvar(produto);
  }

  @Override
  public Produto atualizarProduto(UUID codigo, Produto produto) throws ProdutoNotFoundException {
    Produto produtoAtu = this.produtoRepository.buscarPeloCodigo(codigo);
    if (Objects.nonNull(produtoAtu) && produto.getId().equals(codigo)) {
      produtoAtu.atualiza(produto);
      return this.produtoRepository.salvar(produtoAtu);
    } else {
      throw new ProdutoNotFoundException("Produto não apresenta o ID correto");
    }
  }

  @Override
  public boolean excluirProduto(UUID codigo) throws ProdutoNotFoundException {
    Produto produtoASerExcluido = this.produtoRepository.buscarPeloCodigo(codigo);
    if (Objects.nonNull(produtoASerExcluido)) {
      return this.produtoRepository.excluir(codigo);
    } else {
      throw new ProdutoNotFoundException("Produto não apresenta o ID correto");
    }
  }

  /**
   * @param pageable informações de paginação
   * @return produtos com paginação
   */
  @Override
  public Page<Produto> buscarProdutosPaginados(Pageable pageable) {
    return this.produtoRepository.buscarTodos(pageable);
  }


  @Override
  public List<Produto> buscarProdutosPorCategoria(Categoria categoria) {
    return this.produtoRepository.buscarPorCategoria(categoria);
  }

  @Override
  public List<Produto> buscarProdutosDiponiveisPorCategoria(Categoria categoria) {
    //fixo por produtos disponíveis
    return this.produtoRepository.buscarPorCategoriaESituacao(categoria, SituacaoProduto.DISPONIVEL);
  }

}
