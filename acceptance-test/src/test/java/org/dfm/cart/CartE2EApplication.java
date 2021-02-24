package org.dfm.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.dfm.cart.domain.CartDomain;
import org.dfm.cart.domain.port.ObtainCart;
import org.dfm.cart.domain.port.RequestCart;
import org.dfm.cart.repository.config.JpaAdapterConfig;

@SpringBootApplication
public class CartE2EApplication {

  public static void main(String[] args) {
    SpringApplication.run(CartE2EApplication.class);
  }

  @TestConfiguration
  @Import(JpaAdapterConfig.class)
  static class CartConfig {

    @Bean
    public RequestCart getRequestCart(final ObtainCart obtainCart) {
      return new CartDomain(obtainCart);
    }
  }
}
