package org.dfm.cart.repository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.dfm.cart.domain.port.ObtainCart;
import org.dfm.cart.repository.dao.CartDao;

@SpringBootApplication
public class CartJpaAdapterApplication {

  public static void main(String[] args) {
    SpringApplication.run(CartJpaAdapterApplication.class, args);
  }

  @TestConfiguration
  static class CartJpaTestConfig {

    @Bean
    public ObtainCart getObtainCartRepository(CartDao cartDao) {
      return new CartRepository(cartDao);
    }
  }
}
