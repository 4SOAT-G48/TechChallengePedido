package br.com.fiap.soat.grupo48.pedido.application.service.exception;

import br.com.fiap.soat.grupo48.commons.exception.ApplicationException;

public class ClienteNaoInformadoException extends ApplicationException {
  
  public ClienteNaoInformadoException(String s) {
    super(s);
  }
}
