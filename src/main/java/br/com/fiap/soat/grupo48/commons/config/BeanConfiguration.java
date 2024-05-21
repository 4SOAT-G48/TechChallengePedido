package br.com.fiap.soat.grupo48.commons.config;

import br.com.fiap.soat.grupo48.pedido.application.service.PedidoEmAndamentoUseCaseImpl;
import br.com.fiap.soat.grupo48.pedido.application.service.PedidoSituacaoUseCaseImpl;
import br.com.fiap.soat.grupo48.pedido.application.service.port.in.IPedidoEmAndamentoPort;
import br.com.fiap.soat.grupo48.pedido.application.service.port.in.IPedidoSituacaoPort;
import br.com.fiap.soat.grupo48.pedido.application.service.port.out.IPedidoRepositoryGateway;
import br.com.fiap.soat.grupo48.produto.application.service.ManutecaoProdutoUsecaseImpl;
import br.com.fiap.soat.grupo48.produto.application.service.port.in.IProdutoPort;
import br.com.fiap.soat.grupo48.produto.application.service.port.out.IProdutoRepositoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

  @Bean
  IProdutoPort manutencaoProdutoUseCase(IProdutoRepositoryGateway produtoRepositoryGateway) {
    return new ManutecaoProdutoUsecaseImpl(produtoRepositoryGateway);
  }

  @Bean
  IPedidoEmAndamentoPort pedidoUseCase(IPedidoRepositoryGateway pedidoRepositoryGateway,
                                       IProdutoRepositoryGateway produtoRepositoryGateway) {
    return new PedidoEmAndamentoUseCaseImpl(pedidoRepositoryGateway, produtoRepositoryGateway);
  }

  @Bean
  IPedidoSituacaoPort pedidoSituacaoPort(IPedidoRepositoryGateway pedidoRepositoryGateway) {
    return new PedidoSituacaoUseCaseImpl(pedidoRepositoryGateway);
  }
}
