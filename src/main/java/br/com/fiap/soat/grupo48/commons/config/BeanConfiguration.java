package br.com.fiap.soat.grupo48.commons.config;

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

//  @Bean
//  IProdutoPedidoEmAndamentoPort produtoPedidoUseCase(IProdutoPedidoRepositoryGateway produtoPedidoRepositoryGateway) {
//    return new ProdutoPedidoUseCaseImpl(produtoPedidoRepositoryGateway);
//  }

//  @Bean
//  IPedidoEmAndamentoPort pedidoUseCase(IPedidoRepositoryGateway pedidoRepositoryGateway,
//                                       IClienteRepositoryGateway clienteRepositoryGateway,
//                                       IProdutoRepositoryGateway produtoRepositoryGateway,
//                                       IMetodoPagamentoRepositoryGateway metodoPagamentoRepositoryGateway,
//                                       IPagamentoRepositoryGateway pagamentoRepositoryGateway) {
//    return new PedidoEmAndamentoUseCaseImpl(pedidoRepositoryGateway, clienteRepositoryGateway, produtoRepositoryGateway, metodoPagamentoRepositoryGateway, pagamentoRepositoryGateway);
//  }
//
//  @Bean
//  IPedidoSituacaoPort pedidoSituacaoPort(IPedidoRepositoryGateway pedidoRepositoryGateway) {
//    return new PedidoSituacaoUseCaseImpl(pedidoRepositoryGateway);
//  }
//
//  @Bean
//  IMetodoPagamentoPort metodoPagamentoUseCase(IMetodoPagamentoRepositoryGateway metodoPagamentoRepositoryGateway) {
//    return new MetodoPagamentoUsecaseImpl(metodoPagamentoRepositoryGateway);
//  }
//
//  @Bean
//  IPagamentoSituacaoPort pagamentoSituacaoUseCase(IPagamentoRepositoryGateway pagamentoRepositoryGateway, IPedidoRepositoryGateway pedidoRepositoryGateway, IConsultaInformacaoPagamentoIntegartionGateway consultaInformacaoPagamentoIntegartionGateway) {
//    return new PagamentoSituacaoUsecaseImpl(pagamentoRepositoryGateway, pedidoRepositoryGateway, consultaInformacaoPagamentoIntegartionGateway);
//  }
}
