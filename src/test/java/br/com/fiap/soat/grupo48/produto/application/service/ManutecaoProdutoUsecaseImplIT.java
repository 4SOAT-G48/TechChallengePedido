package br.com.fiap.soat.grupo48.produto.application.service;

import br.com.fiap.soat.grupo48.produto.application.service.exception.ProdutoNotFoundException;
import br.com.fiap.soat.grupo48.produto.infra.adapter.db.ProdutoRepositoryGateway;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
class ManutecaoProdutoUsecaseImplIT {

  @Autowired
  private ProdutoRepositoryGateway produtoRepositoryGateway;

  @Nested
  class atualizarProduto {
    @Test
    void testAtualizar() throws ProdutoNotFoundException {
      fail("Teste não implementado");
    }

    @Test
    void testRetornarErroQuandoIdNaoExiste() {
      fail("Teste não implementado");
    }

    @Test
    void deveRetornarErroQuandoIdsDiferentes() throws Exception {
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
      fail("Teste não implementado");
    }
  }

  @Nested
  class excluirProduto {

    @Test
    void testExcluir() throws ProdutoNotFoundException {
      fail("Teste não implementado");
    }

    @Test
    void testErroQuandoIdNaoExiste() throws Exception {
      fail("Teste não implementado");
    }

  }
}