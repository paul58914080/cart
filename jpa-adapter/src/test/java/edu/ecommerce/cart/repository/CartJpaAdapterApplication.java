package edu.ecommerce.cart.repository;

import net.lbruun.springboot.preliquibase.PreLiquibaseAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import edu.ecommerce.cart.repository.config.JpaAdapterConfig;

@SpringBootApplication
public class CartJpaAdapterApplication {

  public static void main(String[] args) {
    SpringApplication.run(CartJpaAdapterApplication.class, args);
  }

  @TestConfiguration
  @Import(JpaAdapterConfig.class)
  @ImportAutoConfiguration({PreLiquibaseAutoConfiguration.class})
  static class CartJpaTestConfig {}
}
