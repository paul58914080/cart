package org.dfm.cart.repository.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.dfm.cart.domain.port.ObtainCart;
import org.dfm.cart.repository.CartRepository;
import org.dfm.cart.repository.dao.CartDao;

@Configuration
@EntityScan("org.dfm.cart.repository.entity")
@EnableJpaRepositories("org.dfm.cart.repository.dao")
public class JpaAdapterConfig {

  @Bean
  public ObtainCart getCartRepository(CartDao cartDao) {
    return new CartRepository(cartDao);
  }
}
