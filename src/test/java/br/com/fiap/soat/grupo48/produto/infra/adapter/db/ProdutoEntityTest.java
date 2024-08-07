package br.com.fiap.soat.grupo48.produto.infra.adapter.db;

import br.com.fiap.soat.grupo48.produto.domain.model.Categoria;
import br.com.fiap.soat.grupo48.produto.domain.model.SituacaoProduto;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProdutoEntityTest {

    @Nested
    class construtores {
        @Test
        void testConstrutor() {
            ProdutoEntity produtoEntity = new ProdutoEntity();
            assertNotNull(produtoEntity);
        }

        @Test
        void testConstrutorComParametros() {
            ProdutoEntity produtoEntity =
                new ProdutoEntity(
                    UUID.fromString("46934bbf-ef63-42ef-b3ff-3d4aaa5ae58d"),
                    "Produto 1",
                    Categoria.LANCHE,
                    10.0,
                    "Descrição produto 1",
                    SituacaoProduto.DISPONIVEL,
                    new ArrayList<>());
            assertNotNull(produtoEntity);
            assertEquals(UUID.fromString("46934bbf-ef63-42ef-b3ff-3d4aaa5ae58d"), produtoEntity.getId());
            assertEquals("Produto 1", produtoEntity.getNome());
            assertEquals(Categoria.LANCHE, produtoEntity.getCategoria());
            assertEquals(10.0, produtoEntity.getPreco());
            assertEquals("Descrição produto 1", produtoEntity.getDescricao());
            assertEquals(SituacaoProduto.DISPONIVEL, produtoEntity.getSituacao());
        }

        @Test
        void testConstrutorComParametrosNull() {
            ProdutoEntity produtoEntity = new ProdutoEntity(null, null, null, null, null, null, null);
            assertNotNull(produtoEntity);
        }
    }

    @Nested
    class atribucoesCampos {

        @Test
        void testSetters() {
            ProdutoEntity produtoEntity = new ProdutoEntity();
            produtoEntity.setId(UUID.fromString("d71433c1-298f-4dff-b497-a728b88b6ab6"));
            produtoEntity.setNome("Produto 1");
            produtoEntity.setCategoria(Categoria.LANCHE);
            produtoEntity.setPreco(10.0);
            produtoEntity.setDescricao("Descrição produto 1");
            produtoEntity.setSituacao(SituacaoProduto.DISPONIVEL);
            produtoEntity.setImages(new ArrayList<>());

            assertNotNull(produtoEntity);
            assertEquals(UUID.fromString("d71433c1-298f-4dff-b497-a728b88b6ab6"), produtoEntity.getId());
            assertEquals("Produto 1", produtoEntity.getNome());
            assertEquals(Categoria.LANCHE, produtoEntity.getCategoria());
            assertEquals(10.0, produtoEntity.getPreco());
            assertEquals("Descrição produto 1", produtoEntity.getDescricao());
            assertEquals(SituacaoProduto.DISPONIVEL, produtoEntity.getSituacao());
        }
    }
}