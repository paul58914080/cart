package edu.ecommerce.cart.repository.config;

import edu.ecommerce.cart.domain.port.ObtainCart;
import edu.ecommerce.cart.repository.CartRepository;
import edu.ecommerce.cart.repository.dao.CartDao;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("edu.ecommerce.cart.repository.entity")
@EnableJpaRepositories(
    basePackages = "edu.ecommerce.cart.repository.dao",
    repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class JpaAdapterConfig {

  @Bean
  public ObtainCart getCartRepository(CartDao cartDao) {
    return new CartRepository(cartDao);
  }
}
