package br.com.fiap.soat.grupo48.commons.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
public class ApplicationConfiguration {

  /**
   * Tratamento de encode da aplicação.
   *
   * @return o filtro de tratamento de encode.
   */
  @Bean
  public CharacterEncodingFilter characterEncodingFilter() {
    CharacterEncodingFilter filter = new CharacterEncodingFilter();
    filter.setEncoding("UTF-8");
    filter.setForceEncoding(true);
    return filter;
  } // (1
}
