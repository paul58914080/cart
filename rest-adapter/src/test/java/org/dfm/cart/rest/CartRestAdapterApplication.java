package org.dfm.cart.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.dfm.cart.domain.port.RequestCart;

@SpringBootApplication
@ComponentScan(basePackages = "org.dfm.cart")
public class CartRestAdapterApplication {

  @MockBean
  private RequestCart requestCart;

  public static void main(String[] args) {
    SpringApplication.run(CartRestAdapterApplication.class, args);
  }
}
