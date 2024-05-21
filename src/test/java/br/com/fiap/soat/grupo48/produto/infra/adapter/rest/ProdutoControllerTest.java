package br.com.fiap.soat.grupo48.produto.infra.adapter.rest;

import br.com.fiap.soat.grupo48.produto.application.domain.model.Produto;
import br.com.fiap.soat.grupo48.produto.application.service.ManutecaoProdutoUsecaseImpl;
import br.com.fiap.soat.grupo48.produto.application.service.exception.ProdutoNotFoundException;
import br.com.fiap.soat.grupo48.produto.infra.adapter.db.ProdutoRepositoryGateway;
import br.com.fiap.soat.grupo48.produto.infra.adapter.db.SpringProdutoRepository;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProdutoControllerTest {

  AutoCloseable mock;
  /**
   * Variável double para os teste de controllers
   */
  private MockMvc mockMvc;
  @Mock
  private SpringProdutoRepository springProdutoRepository;

  /**
   * Mock da classe ManutecaoProdutoUsecaseImpl
   */
  @Mock
  private ManutecaoProdutoUsecaseImpl manutecaoProdutoUsecase;

  @BeforeEach
  void setUp() {
    mock = MockitoAnnotations.openMocks(this);
    ProdutoRepositoryGateway produtoRepositoryGateway = new ProdutoRepositoryGateway(springProdutoRepository);
    ProdutoController produtoController = new ProdutoController(manutecaoProdutoUsecase);
    mockMvc = MockMvcBuilders.standaloneSetup(produtoController)
        .addFilter((request, response, chain) -> {
          response.setCharacterEncoding("UTF-8");
          chain.doFilter(request, response);
        })
        .build();
  }

  @AfterEach
  void tearDown() throws Exception {
    mock.close();
  }


  @Nested
  class CadastrarProduto {
    @Test
    void testCriarProduto() throws Exception {
      // Arrange
      var produtoRequest = ProdutoHelper.gerarProduto();
      when(manutecaoProdutoUsecase.criarProduto(any(Produto.class)))
          .thenAnswer(i -> {
            Produto produtoCriado = (Produto) i.getArguments()[0];
            produtoCriado.setId(UUID.randomUUID());
            return produtoCriado;
          });

      // Act & Assert
      mockMvc.perform(
          post("/api/produtos")
              .contentType(MediaType.APPLICATION_JSON)
              .content(ProdutoHelper.asJsonString(produtoRequest))
      ).andExpect(status().isCreated());

      verify(manutecaoProdutoUsecase, times(1))
          .criarProduto(any(Produto.class));
    }

    @Test
    void deveRetornarUnsupportedMediaTypeQuandoPassadoPayloadXML() throws Exception {
      // Arrange
      var produtoRequest = ProdutoHelper.gerarProduto();

      // Act & Assert
      mockMvc.perform(
          post("/api/produtos")
              .contentType(MediaType.APPLICATION_XML)
              .content(ProdutoHelper.asXmlString(produtoRequest))
      ).andExpect(status().isUnsupportedMediaType());

      verify(manutecaoProdutoUsecase, never())
          .criarProduto(any(Produto.class));
    }
  }

  @Nested
  class AtualizarProduto {
    @Test
    void testAtualizarProduto() throws Exception {
      // Arrange
      var id = UUID.fromString("3ac9007d-e0cc-46ca-ad05-edb67f47fa36");
      var produto = ProdutoHelper.gerarProduto();
      produto.setId(id);
      var produtoRequest = ProdutoHelper.gerarProduto();
      produtoRequest.setId(id);
      when(manutecaoProdutoUsecase.atualizarProduto(any(UUID.class), any(Produto.class)))
          .thenReturn(produto);

      // Act & Assert
      mockMvc.perform(put("/api/produtos/{id}", id)
              .contentType(MediaType.APPLICATION_JSON)
              .content(ProdutoHelper.asJsonString(produtoRequest)))
          .andExpect(status().isAccepted());
      verify(manutecaoProdutoUsecase, times(1))
          .atualizarProduto(any(UUID.class), any(Produto.class));
    }

    @Test
    void deveRetornarUnsupportedMediaType() throws Exception {
      // Arrange
      var id = UUID.fromString("3ac9007d-e0cc-46ca-ad05-edb67f47fa36");
      var produtoRequest = ProdutoHelper.gerarProduto();
      produtoRequest.setId(id);

      // Act & Assert
      mockMvc.perform(put("/api/produtos/{id}", id)
              .contentType(MediaType.APPLICATION_XML)
              .content(ProdutoHelper.asXmlString(produtoRequest)))
          .andExpect(status().isUnsupportedMediaType());
      verify(manutecaoProdutoUsecase, never())
          .buscarPeloId(any(UUID.class));
      verify(manutecaoProdutoUsecase, never())
          .atualizarProduto(any(UUID.class), any(Produto.class));
    }

    @Test
    void deveRetornarBadRequestQuandoIdNaoExiste() throws Exception {
      // Arrange
      var id = UUID.fromString("527d522b-1793-4b6b-b68d-fdc479900858");
      var produtoRequest = ProdutoHelper.gerarProduto();
      produtoRequest.setId(id);
      var mensagemErro = "Produto não apresenta o ID correto";
      when(manutecaoProdutoUsecase.atualizarProduto(any(UUID.class), any(Produto.class)))
          .thenThrow(new ProdutoNotFoundException(mensagemErro));

      // Act & Assert
      mockMvc.perform(put("/api/produtos/{id}", id)
              .contentType(MediaType.APPLICATION_JSON)
              .content(ProdutoHelper.asJsonString(produtoRequest)))
          //só usar durante o desenvolvimento do teste
          //.andDo(print())
          .andExpect(status().isBadRequest())
          .andExpect(content().string(mensagemErro));
      verify(manutecaoProdutoUsecase, times(1))
          .atualizarProduto(any(UUID.class), any(Produto.class));
    }

    @Test
    void deveBadRequestQuandoOsIdsSaoDiferentes() throws Exception {
      // Arrange
      var id = UUID.fromString("5b4781e6-97ca-4425-a1ac-8151df853f1f");
      var produtoRequest = ProdutoHelper.gerarProduto();
      produtoRequest.setId(UUID.fromString("4b39772d-aad8-4881-ac22-fb8632365634"));
      var mensagemErro = "Produto não apresenta o ID correto";
      when(manutecaoProdutoUsecase.atualizarProduto(any(UUID.class), any(Produto.class)))
          .thenThrow(new ProdutoNotFoundException(mensagemErro));

      // Act & Assert
      mockMvc.perform(put("/api/produtos/{id}", id)
              .contentType(MediaType.APPLICATION_JSON)
              .content(ProdutoHelper.asJsonString(produtoRequest)))
          //só usar durante o desenvolvimento do teste
          //.andDo(print())
          .andExpect(status().isBadRequest())
          .andExpect(content().string(mensagemErro));
      verify(manutecaoProdutoUsecase, times(1))
          .atualizarProduto(any(UUID.class), any(Produto.class));
    }
  }

  @Nested
  class BuscarProduto {
    @Test
    void testBuscarProduto() throws Exception {
      // Arrange
      var id = UUID.fromString("9a92c26e-698f-423d-b881-1a847e2436a5");
      var produto = ProdutoHelper.gerarProduto();
      produto.setId(id);
      when(manutecaoProdutoUsecase.buscarPeloId(any(UUID.class)))
          .thenReturn(produto);

      // Act & Assert
      mockMvc.perform(get("/api/produtos/{id}", id)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());
      verify(manutecaoProdutoUsecase, times(1))
          .buscarPeloId(any(UUID.class));
    }

    @Test
    void deveGerarExceptionQuandoIdNaoExiste() throws Exception {
      // Arrange
      var id = UUID.fromString("527d522b-1793-4b6b-b68d-fdc479900858");

      when(manutecaoProdutoUsecase.buscarPeloId(any(UUID.class)))
          .thenThrow(new ProdutoNotFoundException("Produto não encontrado"));

      // Act & Assert
      mockMvc.perform(get("/api/produtos/{id}", id))
          .andExpect(status().isNotFound());
      verify(manutecaoProdutoUsecase, times(1))
          .buscarPeloId(any(UUID.class));
    }
  }


  @Nested
  class ExcluirProduto {
    @Test
    void testExcluirProduto() throws Exception {
      // Arrange
      var id = UUID.fromString("5fa552da-b9c5-4975-9519-d7e4061eea8a");
      var produto = ProdutoHelper.gerarProduto();
      produto.setId(id);
      when(manutecaoProdutoUsecase.excluirProduto(any(UUID.class)))
          .thenReturn(true);

      // Act & Assert
      mockMvc.perform(delete("/api/produtos/{id}", id)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().string("Produto excluído"));
      verify(manutecaoProdutoUsecase, times(1))
          .excluirProduto(any(UUID.class));
    }

    @Test
    void deveRetornarBadRequestQuandoIdNaoExiste() throws Exception {
      // Arrange
      var id = UUID.fromString("527d522b-1793-4b6b-b68d-fdc479900858");
      var produtoRequest = ProdutoHelper.gerarProduto();
      produtoRequest.setId(id);
      var mensagemErro = "Produto não apresenta o ID correto";
      when(manutecaoProdutoUsecase.excluirProduto(any(UUID.class)))
          .thenThrow(new ProdutoNotFoundException(mensagemErro));

      // Act & Assert
      mockMvc.perform(delete("/api/produtos/{id}", id)
              .contentType(MediaType.APPLICATION_JSON))
          //só usar durante o desenvolvimento do teste
          //.andDo(print())
          .andExpect(status().isBadRequest())
          .andExpect(content().string(mensagemErro));
      verify(manutecaoProdutoUsecase, times(1))
          .excluirProduto(any(UUID.class));
    }
  }

  @Nested
  class ListarProdutos {
    @Test
    void testBuscarProdutos() throws Exception {
      // Arrange
      var listaProdutos = Arrays.asList(
          ProdutoHelper.gerarProduto(),
          ProdutoHelper.gerarProduto());
      //para cada item da lista de produtos settar o id
      listaProdutos.forEach(produto -> produto.setId(UUID.randomUUID()));

      when(manutecaoProdutoUsecase.buscarProdutos())
          .thenReturn(listaProdutos);

      // Act & Assert
      mockMvc.perform(get("/api/produtos")
              .contentType(MediaType.APPLICATION_JSON))
          //.andDo(print())
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(2)))
          .andExpect(jsonPath("$[0].id").isNotEmpty())
          .andExpect(jsonPath("$[0].nome").isNotEmpty())
          .andExpect(jsonPath("$[0].descricao").isNotEmpty())
          .andExpect(jsonPath("$[0].preco").isNotEmpty())
          .andExpect(jsonPath("$[0].categoria").isNotEmpty())
          .andExpect(jsonPath("$[0].situacao").isNotEmpty())
          .andExpect(jsonPath("$[1].id").isNotEmpty())
          .andExpect(jsonPath("$[1].nome").isNotEmpty())
          .andExpect(jsonPath("$[1].descricao").isNotEmpty())
          .andExpect(jsonPath("$[1].preco").isNotEmpty())
          .andExpect(jsonPath("$[1].categoria").isNotEmpty())
          .andExpect(jsonPath("$[1].situacao").isNotEmpty())
      ;
    }

    @Test
    void testBuscarProdutosPaginados() throws Exception {
      // Arrange
      var produto = ProdutoHelper.gerarProduto();
      //produtos.setId(UUID.fromString("3ac9007d-e0cc-46ca-ad05-edb67f47fa64"));
      Pageable pageable = PageRequest.of(0, 1); // Página 0, 10 elementos por página
      List<Produto> produtos = Collections.singletonList(produto);
      Page<Produto> page = new PageImpl<>(produtos, pageable, produtos.size());
      when(manutecaoProdutoUsecase.buscarProdutosPaginados(any(Pageable.class)))
          .thenReturn(page);

      // Act & Assert
      mockMvc.perform(get("/api/produtos/paginados")
              .contentType(MediaType.APPLICATION_JSON)
              .param("page", "0")
              .param("size", "10"))
          //.andDo(print())
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.content", not(empty())))
          .andExpect(jsonPath("$.totalPages").value(1))
          .andExpect(jsonPath("$.totalElements").value(1))
          .andExpect(jsonPath("$.size").value(1))
      ;
    }

    @Test
    void testBuscarProdutosPaginadosQuandoNaoInformadaPaginacao() throws Exception {
      // Arrange
      var produto = ProdutoHelper.gerarProduto();
      Pageable pageable = PageRequest.of(0, 1); // Página 0, 10 elementos por página
      List<Produto> produtos = Collections.singletonList(produto);
      Page<Produto> page = new PageImpl<>(produtos, pageable, produtos.size());
      when(manutecaoProdutoUsecase.buscarProdutosPaginados(any(Pageable.class)))
          .thenReturn(page);

      // Act & Assert
      mockMvc.perform(get("/api/produtos/paginados"))
          //.andDo(print())
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.content", not(empty())))
          .andExpect(jsonPath("$.totalPages").value(1))
          .andExpect(jsonPath("$.totalElements").value(1))
          .andExpect(jsonPath("$.size").value(1))
      ;
    }
  }
}