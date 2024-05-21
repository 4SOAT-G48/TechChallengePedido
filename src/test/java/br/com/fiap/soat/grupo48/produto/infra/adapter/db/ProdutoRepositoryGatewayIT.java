package br.com.fiap.soat.grupo48.produto.infra.adapter.db;

import br.com.fiap.soat.grupo48.produto.application.domain.model.Produto;
import br.com.fiap.soat.grupo48.produto.application.service.exception.ProdutoNotFoundException;
import br.com.fiap.soat.grupo48.produto.utils.ProdutoHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
class ProdutoRepositoryGatewayIT {

  @Autowired
  private ProdutoRepositoryGateway produtoRepositoryGateway;

  @Nested
  class atualizarProduto {
    @Test
    void testAtualizar() throws ProdutoNotFoundException {
      // Arrange
      var id = UUID.fromString("fe91ab2c-289b-4023-9f2d-a2e00056d84d");

      // Act
      var produtoEncontrado = produtoRepositoryGateway.buscarPeloId(id);
      String testeDeAlteracao = "Teste de alteração";
      produtoEncontrado.setDescricao(testeDeAlteracao);
      produtoRepositoryGateway.salvar(produtoEncontrado);
      //Busca novamente da base para garantir as alterações
      produtoEncontrado = produtoRepositoryGateway.buscarPeloId(id);

      // Assert
      assertThat(produtoEncontrado)
          .isInstanceOf(Produto.class)
          .isNotNull();
      assertThat(produtoEncontrado.getId())
          .isNotNull()
          .isEqualTo(id);
      assertThat(produtoEncontrado.getDescricao())
          .isEqualTo(testeDeAlteracao);
    }

    @Test
    void testRetornarErroQuandoIdNaoExiste() {
      fail("Teste não implementado");
    }
  }

  @Nested
  class buscarProdutos {
    @Test
    void testBuscarPeloId() {
      fail("Teste não implementado");
    }

    @Test
    void testBuscarProdutosDiponiveisPorCategoria() {
      fail("Teste não implementado");
    }

    @Test
    void testBuscarProdutosPaginados() {
      fail("Teste não implementado");
    }

    @Test
    void testBuscarProdutosPorCategoria() {
      fail("Teste não implementado");
    }

    @Test
    void testBuscarProdutos() {
      fail("Teste não implementado");
    }
  }

  @Nested
  class criarProduto {
    @Test
    void testCriarProduto() throws ProdutoNotFoundException {
      // Arrange
      var produto = ProdutoHelper.gerarProduto();

      // Act
      var produtoCadastrado = produtoRepositoryGateway.salvar(produto);

      // Assert
      assertThat(produtoCadastrado)
          .isInstanceOf(Produto.class)
          .isNotNull();
      assertThat(produtoCadastrado.getId()).isNotNull();
    }
  }

  @Nested
  class excluirProduto {

    @Test
    void testExcluir() throws ProdutoNotFoundException {
      // Arrange
      var id = UUID.fromString("bf385ed8-5c2d-4fd1-b37d-6c141589a4be");

      // Act
      boolean excluired = produtoRepositoryGateway.excluir(id);

      // Assert
      assertThat(excluired).isTrue();
    }

    @Test
    void testErroQuandoIdNaoExiste() throws Exception {
      // Arrange
      var id = UUID.fromString("0d0dfbe7-c7ec-4b2a-9fde-30e711905ac5");

      // Act & Assert
      assertThatThrownBy(() -> produtoRepositoryGateway.excluir(id))
          .isInstanceOf(ProdutoNotFoundException.class)
          .hasMessage("Não foi possível excluir o produto");
    }

  }
}