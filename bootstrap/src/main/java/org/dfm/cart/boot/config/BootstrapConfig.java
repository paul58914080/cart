package org.dfm.cart.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.dfm.cart.domain.CartDomain;
import org.dfm.cart.domain.port.ObtainCart;
import org.dfm.cart.domain.port.RequestCart;
import org.dfm.cart.repository.config.JpaAdapterConfig;

@Configuration
@Import(JpaAdapterConfig.class)
public class BootstrapConfig {

  @Bean
  public RequestCart getRequestCart(ObtainCart obtainCart) {
    return new CartDomain(obtainCart);
  }
}
