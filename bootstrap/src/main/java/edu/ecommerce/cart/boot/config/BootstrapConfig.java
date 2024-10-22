package edu.ecommerce.cart.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import edu.ecommerce.cart.domain.CartDomain;
import edu.ecommerce.cart.domain.port.ObtainCart;
import edu.ecommerce.cart.domain.port.RequestCart;
import edu.ecommerce.cart.repository.config.JpaAdapterConfig;

@Configuration
@Import(JpaAdapterConfig.class)
public class BootstrapConfig {

  @Bean
  public RequestCart getRequestCart(ObtainCart obtainCart) {
    return new CartDomain(obtainCart);
  }
}
