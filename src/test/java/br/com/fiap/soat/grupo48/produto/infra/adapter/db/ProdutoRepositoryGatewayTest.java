package br.com.fiap.soat.grupo48.produto.infra.adapter.db;

import br.com.fiap.soat.grupo48.pedido.utils.ProdutoHelper;
import br.com.fiap.soat.grupo48.produto.application.domain.model.Categoria;
import br.com.fiap.soat.grupo48.produto.application.domain.model.Produto;
import br.com.fiap.soat.grupo48.produto.application.domain.model.SituacaoProduto;
import br.com.fiap.soat.grupo48.produto.application.service.exception.ProdutoNotFoundException;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProdutoRepositoryGatewayTest {


  AutoCloseable mock;
  private ProdutoRepositoryGateway produtoRepositoryGateway;
  @Mock
  private SpringProdutoRepository springProdutoRepository;

  @BeforeEach
  void setUp() {
    mock = MockitoAnnotations.openMocks(this);
    produtoRepositoryGateway = new ProdutoRepositoryGateway(springProdutoRepository);
  }

  @AfterEach
  void tearDown() throws Exception {
    mock.close();
  }

  @Nested
  class atualizarProduto {
    @Test
    void testAtualizar() throws ProdutoNotFoundException {
      // Arrange
      var id = UUID.randomUUID();
      var produtoAntigo = ProdutoHelper.gerarProduto();
      produtoAntigo.setId(id);
      // clonar o produto antigo para não alterar o original
      var produtoAtualizado = ProdutoHelper.gerarProduto();
      produtoAtualizado.setId(id);
      produtoAtualizado.setNome("Hambúrger");

      when(springProdutoRepository.findById(id))
          .thenReturn(Optional.of(new ProdutoEntity(produtoAntigo)));
      when(springProdutoRepository.save(any(ProdutoEntity.class)))
          .thenReturn(new ProdutoEntity(produtoAtualizado));


      // Act
      var produtoEncontrado = produtoRepositoryGateway.salvar(produtoAtualizado);

      // Assert
      assertThat(produtoEncontrado)
          .isNotEqualTo(produtoAntigo)
          .isEqualTo(produtoAtualizado);
      verify(springProdutoRepository, times(1)).findById(id);
      verify(springProdutoRepository, times(1)).save(any(ProdutoEntity.class));

    }

    @Test
    void testRetornarErroQuandoIdNaoExiste() {
      // Arrange
      var id = UUID.fromString("ba55aa2a-134b-44e4-a435-8f6c94a74e0a");
      var produtoAntigo = ProdutoHelper.gerarProduto();
      produtoAntigo.setId(id);
      // clonar o produto antigo para não alterar o original
      var produtoAtualizado = ProdutoHelper.gerarProduto();
      produtoAtualizado.setId(id);
      produtoAtualizado.setNome("Hambúrger");

      when(springProdutoRepository.findById(id))
          .thenReturn(Optional.empty());

      // Act & Assert
      assertThatThrownBy(() -> produtoRepositoryGateway.salvar(produtoAtualizado))
          .isInstanceOf(ProdutoNotFoundException.class)
          .hasMessage("Produto não encontrado com o ID: " + id);
      verify(springProdutoRepository, times(1)).findById(any(UUID.class));
      verify(springProdutoRepository, never()).deleteById(any(UUID.class));
    }
  }

  @Nested
  class buscarProdutos {
    @Test
    @Severity(SeverityLevel.BLOCKER)
    void testBuscarPeloId() throws ProdutoNotFoundException {
      // Arrange
      var id = UUID.randomUUID();
      var produto = ProdutoHelper.gerarProduto();
      produto.setId(id);

      when(springProdutoRepository.findById(id))
          .thenReturn(java.util.Optional.of(new ProdutoEntity(produto)));

      // Act
      var produtoEncontrado = produtoRepositoryGateway.buscarPeloId(id);

      // Assert
      assertThat(produtoEncontrado)
          .isInstanceOf(Produto.class)
          .isEqualTo(produto);
      verify(springProdutoRepository, times(1)).findById(any(UUID.class));

    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Valida o cenário de exeção ao efetuar uma busca do produto quando o ID não existir")
    void testGerarExceptionQuandoIdNaoExiste() {
      // Arrange
      var id = UUID.randomUUID();

      when(springProdutoRepository.findById(id))
          .thenReturn(Optional.empty());

      // Act & Assert
      assertThatThrownBy(() -> produtoRepositoryGateway.buscarPeloId(id))
          .isInstanceOf(ProdutoNotFoundException.class)
          .hasMessage("Produto não encontrado com o ID: " + id);
      verify(springProdutoRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void testBuscarProdutosPorCategoriaESituacao() {
      // Arrange
      var listaProdutos = Arrays.asList(
          ProdutoHelper.gerarProdutoEntity(),
          ProdutoHelper.gerarProdutoEntity()
      );
      List<Produto> produtos = listaProdutos.stream().map(ProdutoEntity::toProduto).toList();

      when(springProdutoRepository.findByCategoriaAndSituacao(any(), any()))
          .thenReturn(listaProdutos);

      // Act
      var listaProdutosEncontrados = produtoRepositoryGateway.buscarPorCategoriaESituacao(Categoria.LANCHE, SituacaoProduto.DISPONIVEL);

      // Assert
      assertThat(listaProdutosEncontrados)
          .isNotNull()
          .isNotEmpty()
          .hasSize(2)
          .containsExactlyInAnyOrderElementsOf(produtos)
          .allSatisfy(produto -> assertThat(produto.getCategoria()).isEqualTo(Categoria.LANCHE))
          .allSatisfy(produto -> assertThat(produto.getSituacao()).isEqualTo(SituacaoProduto.DISPONIVEL));
      verify(springProdutoRepository, times(1))
          .findByCategoriaAndSituacao(Categoria.LANCHE, SituacaoProduto.DISPONIVEL);
    }

    @Test
    void testBuscarProdutosPaginados() {
      // Arrange
      var listaProdutos = Arrays.asList(
          ProdutoHelper.gerarProdutoEntity(),
          ProdutoHelper.gerarProdutoEntity());

      when(springProdutoRepository.findAll(any(Pageable.class)))
          .thenReturn(new PageImpl<>(listaProdutos, PageRequest.of(0, 2), listaProdutos.size()));
      List<Produto> produtos = listaProdutos.stream().map(ProdutoEntity::toProduto).collect(Collectors.toList());

      Pageable pageble = PageRequest.of(0, 10);

      // Act
      var listaProdutosEncontrados = produtoRepositoryGateway.buscarTodos(pageble);

      // Assert
      assertThat(listaProdutosEncontrados)
          .isNotNull()
          .isNotEmpty()
          .hasSize(2)
          .containsExactlyInAnyOrderElementsOf(produtos);
      verify(springProdutoRepository, times(1))
          .findAll(any(Pageable.class));
    }

    @Test
    void testBuscarProdutosPorCategoria() {
      // Arrange
      var listaProdutos = Arrays.asList(
          ProdutoHelper.gerarProdutoEntity(),
          ProdutoHelper.gerarProdutoEntity()
      );
      List<Produto> produtos = listaProdutos.stream().map(ProdutoEntity::toProduto).toList();

      when(springProdutoRepository.findByCategoria(any()))
          .thenReturn(listaProdutos);

      // Act
      var listaProdutosEncontrados = produtoRepositoryGateway.buscarPorCategoria(Categoria.LANCHE);

      // Assert
      assertThat(listaProdutosEncontrados)
          .isNotNull()
          .isNotEmpty()
          .hasSize(2)
          .containsExactlyInAnyOrderElementsOf(produtos)
          .allSatisfy(produto -> assertThat(produto.getCategoria()).isEqualTo(Categoria.LANCHE));
      verify(springProdutoRepository, times(1))
          .findByCategoria(Categoria.LANCHE);
    }

    @Test
    void testBuscarProdutos() {
      // Arrange
      var listaProdutos = Arrays.asList(
          ProdutoHelper.gerarProdutoEntity(),
          ProdutoHelper.gerarProdutoEntity());
      when(springProdutoRepository.findAll())
          .thenReturn(listaProdutos);
      List<Produto> produtos = listaProdutos.stream().map(ProdutoEntity::toProduto).collect(Collectors.toList());

      // Act
      var listaProdutosEncontrados = produtoRepositoryGateway.buscarTodos();

      // Assert
      assertThat(listaProdutosEncontrados)
          .isNotNull()
          .isNotEmpty()
          .hasSize(2)
          .containsExactlyInAnyOrderElementsOf(produtos);
      verify(springProdutoRepository, times(1))
          .findAll();

    }
  }

  @Nested
  class criarProduto {
    @Test
    void testCriarProduto() throws ProdutoNotFoundException {
      // Arrange
      var produto = ProdutoHelper.gerarProduto();
      var produtoEntity = new ProdutoEntity();
      produtoEntity.setId(UUID.randomUUID());
      produtoEntity.setNome(produto.getNome());
      produtoEntity.setDescricao(produto.getDescricao());
      produtoEntity.setPreco(produto.getPreco());
      produtoEntity.setSituacao(produto.getSituacao());
      produtoEntity.setCategoria(produto.getCategoria());

      when(springProdutoRepository.save(any(ProdutoEntity.class)))
          .thenReturn(produtoEntity);


      // Act
      var produtoCadastrado = produtoRepositoryGateway.salvar(produto);

      // Assert
      assertThat(produtoCadastrado)
          .isInstanceOf(Produto.class)
          .isNotNull();

      //antes de cadastrar não tem código ainda
      assertThat(produto.getId()).isNull();
      //o código vai ser gerado pelo serviço
      assertThat(produtoCadastrado.getId()).isNotNull();
      assertThat(produtoCadastrado.getNome()).isEqualTo(produto.getNome());
      assertThat(produtoCadastrado.getDescricao()).isEqualTo(produto.getDescricao());
      assertThat(produtoCadastrado.getPreco()).isEqualTo(produto.getPreco());
      assertThat(produtoCadastrado.getSituacao()).isEqualTo(produto.getSituacao());
      assertThat(produtoCadastrado.getCategoria()).isEqualTo(produto.getCategoria());

      verify(springProdutoRepository, times(1)).save(any(ProdutoEntity.class));

    }
  }

  @Nested
  class excluirProduto {

    @Test
    void testExcluir() throws ProdutoNotFoundException {
      // Arrange
      var id = UUID.randomUUID();
      var produto = ProdutoHelper.gerarProduto();
      produto.setId(id);
      when(springProdutoRepository.findById(id))
          .thenReturn(Optional.of(new ProdutoEntity(produto)));
      doNothing().when(springProdutoRepository).deleteById(any(UUID.class));

      // Act
      boolean excluired = produtoRepositoryGateway.excluir(id);

      // Assert
      assertThat(excluired).isTrue();
      verify(springProdutoRepository, times(1)).findById(id);
      verify(springProdutoRepository, times(1)).deleteById(id);
    }

    @Test
    void testErroQuandoIdNaoExiste() throws Exception {

      // Arrange
      var id = UUID.randomUUID();

      when(springProdutoRepository.findById(id))
          .thenReturn(Optional.empty());

      // Act

      // Assert
      assertThatThrownBy(() -> produtoRepositoryGateway.excluir(id))
          .isInstanceOf(ProdutoNotFoundException.class)
          .hasMessage("Não foi possível excluir o produto");
      verify(springProdutoRepository, times(1)).findById(any(UUID.class));
      verify(springProdutoRepository, never()).deleteById(any(UUID.class));
    }

  }
}