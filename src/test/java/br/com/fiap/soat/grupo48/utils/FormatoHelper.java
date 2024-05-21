package br.com.fiap.soat.grupo48.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public abstract class FormatoHelper {

  public static String asJsonString(final Object object) {
    try {
      return new ObjectMapper().writeValueAsString(object);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static String asXmlString(final Object object) {
    try {
      //retornar o objeto passado em uma string com formato xml
      XmlMapper xmlMapper = new XmlMapper();
      return xmlMapper.writeValueAsString(object);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
