package br.com.fiap.soat.grupo48.produto.application.service;

import br.com.fiap.soat.grupo48.produto.application.domain.model.Categoria;
import br.com.fiap.soat.grupo48.produto.application.domain.model.Produto;
import br.com.fiap.soat.grupo48.produto.application.domain.model.SituacaoProduto;
import br.com.fiap.soat.grupo48.produto.application.service.exception.ProdutoNotFoundException;
import br.com.fiap.soat.grupo48.produto.application.service.port.out.IProdutoRepositoryGateway;
import br.com.fiap.soat.grupo48.produto.utils.ProdutoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ManutecaoProdutoUsecaseImplTest {

  AutoCloseable autoCloseable;
  private ManutecaoProdutoUsecaseImpl manutecaoProdutoUsecase;
  @Mock
  private IProdutoRepositoryGateway produtoRepositoryGateway;

  @BeforeEach
  void setUp() {
    autoCloseable = MockitoAnnotations.openMocks(this);
    manutecaoProdutoUsecase = new ManutecaoProdutoUsecaseImpl(produtoRepositoryGateway);
  }

  @AfterEach
  void tearDown() throws Exception {
    autoCloseable.close();
  }

  @Nested
  class criarProduto {

    @Test
    void criarProduto() throws ProdutoNotFoundException {
      // Arrange
      Produto produto = ProdutoHelper.gerarProduto();

      // Act
      when(produtoRepositoryGateway.salvar(any(Produto.class)))
          .thenAnswer(i -> {
            Produto produtoArgumento = i.getArgument(0);
            produtoArgumento.setId(UUID.randomUUID());
            return produtoArgumento;
          });

      // Assert
      Produto produtoCriado = manutecaoProdutoUsecase.criarProduto(produto);
      assertThat(produtoCriado.getId()).isNotNull();
      assertThat(produtoCriado.getNome()).isEqualTo(produto.getNome());
      assertThat(produtoCriado.getDescricao()).isEqualTo(produto.getDescricao());
      assertThat(produtoCriado.getPreco()).isEqualTo(produto.getPreco());
      assertThat(produtoCriado.getCategoria()).isEqualTo(produto.getCategoria());
      assertThat(produtoCriado.getImagens()).isEqualTo(produto.getImagens());
      assertThat(produtoCriado.getSituacao()).isEqualTo(produto.getSituacao());

    }
  }

  @Nested
  class atualizarProduto {

    @Test
    void atualizarProduto() throws ProdutoNotFoundException {
      // Arrange
      var id = UUID.randomUUID();
      var produtoAntigo = ProdutoHelper.gerarProduto();
      produtoAntigo.setId(id);
      //clonar produto antigo para não alterar o produto original
      var produtoNovo = ProdutoHelper.gerarProduto();
      produtoNovo.setId(id);
      var novoNome = "Novo nome";
      produtoNovo.setNome(novoNome);

      // Act
      when(produtoRepositoryGateway.buscarPeloId(any(UUID.class)))
          .thenReturn(produtoAntigo);
      when(produtoRepositoryGateway.salvar(any(Produto.class)))
          .thenReturn(produtoNovo);

      // Assert
      Produto produtoAtualizado = manutecaoProdutoUsecase.atualizarProduto(produtoAntigo.getId(), produtoAntigo);
      assertThat(produtoAtualizado.getId()).isNotNull();
      assertThat(produtoAtualizado.getNome()).isEqualTo(novoNome);
      assertThat(produtoAtualizado.getDescricao()).isEqualTo(produtoAntigo.getDescricao());
      assertThat(produtoAtualizado.getPreco()).isEqualTo(produtoAntigo.getPreco());
      assertThat(produtoAtualizado.getCategoria()).isEqualTo(produtoAntigo.getCategoria());
      assertThat(produtoAtualizado.getImagens()).isEqualTo(produtoAntigo.getImagens());
      assertThat(produtoAtualizado.getSituacao()).isEqualTo(produtoAntigo.getSituacao());
    }

    @Test
    void deveRetornarErroQuandoIdNaoExiste() throws Exception {
      // Arrange
      var produto = ProdutoHelper.gerarProduto();
      produto.setId(UUID.randomUUID());
      var id = UUID.randomUUID();

      // Act
      when(produtoRepositoryGateway.buscarPeloId(any(UUID.class)))
          .thenReturn(null);

      // Assert
      try {
        manutecaoProdutoUsecase.atualizarProduto(id, produto);
      } catch (ProdutoNotFoundException e) {
        assertThat(e.getMessage()).isEqualTo("Produto não apresenta o ID correto");
      }
    }

    @Test
    void deveRetornarErroQuandoIdsDiferentes() throws Exception {
      // Arrange
      var produto = ProdutoHelper.gerarProduto();
      produto.setId(UUID.randomUUID());
      var id = UUID.randomUUID();
      var outroId = UUID.randomUUID();

      // Act
      when(produtoRepositoryGateway.buscarPeloId(any(UUID.class)))
          .thenReturn(null);

      // Assert
      try {
        manutecaoProdutoUsecase.atualizarProduto(outroId, produto);
      } catch (ProdutoNotFoundException e) {
        assertThat(e.getMessage()).isEqualTo("Produto não apresenta o ID correto");
      }
    }
  }

  @Nested
  class excluirProduto {

    @Test
    void excluirProduto() throws ProdutoNotFoundException {
      // Arrange
      var id = UUID.randomUUID();
      var produto = ProdutoHelper.gerarProduto();
      produto.setId(id);

      // Act
      when(produtoRepositoryGateway.buscarPeloId(any(UUID.class)))
          .thenReturn(produto);
      when(produtoRepositoryGateway.excluir(any(UUID.class)))
          .thenReturn(true);

      // Assert
      boolean excluido = manutecaoProdutoUsecase.excluirProduto(id);
      assertThat(excluido).isTrue();
    }

    @Test
    void deveRetornarErroQuandoIdNaoExiste() throws Exception {
      // Arrange
      var id = UUID.randomUUID();

      // Act
      when(produtoRepositoryGateway.buscarPeloId(any(UUID.class)))
          .thenReturn(null);

      // Assert
      try {
        manutecaoProdutoUsecase.excluirProduto(id);
      } catch (ProdutoNotFoundException e) {
        assertThat(e.getMessage()).isEqualTo("Produto não apresenta o ID correto");
      }
    }
  }

  @Nested
  class buscarProdutos {

    @Test
    void testBuscarProdutos() {
      // Arrange
      var listaProdutos = Arrays.asList(
          ProdutoHelper.gerarProduto(),
          ProdutoHelper.gerarProduto());
      when(produtoRepositoryGateway.buscarTodos())
          .thenReturn(listaProdutos);

      // Act
      var produtos = manutecaoProdutoUsecase.buscarProdutos();

      // Assert
      assertThat(produtos)
          .isNotNull()
          .isNotEmpty()
          .hasSize(2)
          .containsExactlyInAnyOrderElementsOf(listaProdutos);
      verify(produtoRepositoryGateway, times(1))
          .buscarTodos();
    }

    @Test
    void buscarPeloId() throws ProdutoNotFoundException {
      // Arrange
      var id = UUID.randomUUID();
      var produto = ProdutoHelper.gerarProduto();
      produto.setId(id);
      when(produtoRepositoryGateway.buscarPeloId(any(UUID.class)))
          .thenReturn(produto);

      // Act
      var produtoEncontrado = manutecaoProdutoUsecase.buscarPeloId(id);

      // Assert
      assertThat(produtoEncontrado)
          .isNotNull()
          .isEqualTo(produto);
      verify(produtoRepositoryGateway, times(1))
          .buscarPeloId(id);
    }


    @Test
    void buscarProdutosPaginados() {
      // Arrange
      var listaProdutos = Arrays.asList(
          ProdutoHelper.gerarProduto(),
          ProdutoHelper.gerarProduto());

      Pageable pageable = PageRequest.of(0, 1); // Página 0, 10 elementos por página
      //List<Produto> produtos = Collections.singletonList(produto);
      Page<Produto> page = new PageImpl<>(listaProdutos, pageable, listaProdutos.size());
      when(produtoRepositoryGateway.buscarTodos(any(Pageable.class)))
          .thenReturn(page);

      Pageable pageableRequest = PageRequest.of(0, 10);
      // Act
      var produtos = manutecaoProdutoUsecase.buscarProdutosPaginados(pageableRequest);

      // Assert
      assertThat(produtos)
          .isNotNull()
          .isNotEmpty()
          .hasSize(2)
          .containsExactlyInAnyOrderElementsOf(listaProdutos);
      verify(produtoRepositoryGateway, times(1))
          .buscarTodos(any(Pageable.class));
    }

    @Test
    void buscarProdutosPorCategoria() {
      // Arrange
      var produto = ProdutoHelper.gerarProduto();
      produto.setId(UUID.randomUUID());

      when(produtoRepositoryGateway.buscarPorCategoria(Categoria.LANCHE))
          .thenReturn(List.of(produto));

      // Act
      var produtos = manutecaoProdutoUsecase.buscarProdutosPorCategoria(Categoria.LANCHE);

      // Assert
      assertThat(produtos)
          .isNotNull()
          .isNotEmpty()
          .hasSize(1)
          .containsExactlyInAnyOrder(produto);
    }

    @Test
    void buscarProdutosDiponiveisPorCategoria() {
      // Arrange
      var produto = ProdutoHelper.gerarProduto();
      produto.setId(UUID.randomUUID());

      when(produtoRepositoryGateway.buscarPorCategoriaESituacao(Categoria.LANCHE, SituacaoProduto.DISPONIVEL))
          .thenReturn(List.of(produto));

      // Act
      var produtos = manutecaoProdutoUsecase.buscarProdutosDiponiveisPorCategoria(Categoria.LANCHE);

      // Assert
      assertThat(produtos)
          .isNotNull()
          .isNotEmpty()
          .hasSize(1)
          .containsExactlyInAnyOrder(produto);
    }
  }

}